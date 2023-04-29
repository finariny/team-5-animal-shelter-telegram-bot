package com.example.team5animalsheltertelegrambot.service.bot.impl;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import com.example.team5animalsheltertelegrambot.exception.ReportException;
import com.example.team5animalsheltertelegrambot.listener.BotUpdatesListener;
import com.example.team5animalsheltertelegrambot.properties.TelegramProperties;
import com.example.team5animalsheltertelegrambot.repository.AnimalReportRepository;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
import com.example.team5animalsheltertelegrambot.repository.DogShelterRepository;
import com.example.team5animalsheltertelegrambot.repository.person.CustomerRepository;
import com.example.team5animalsheltertelegrambot.service.bot.BotCommandService;
import com.example.team5animalsheltertelegrambot.service.report.impl.AnimalReportServiceImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import liquibase.pro.packaged.S;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.team5animalsheltertelegrambot.configuration.CommandType.*;

@Service
@RequiredArgsConstructor
public class BotCommandServiceImpl implements BotCommandService {
    private final Logger logger = LoggerFactory.getLogger(BotCommandServiceImpl.class);

    private final TelegramBot telegramBot;

    private final TelegramProperties telegramProperties;

    private final CustomerRepository customerRepository;
    private final AnimalReportServiceImpl animalReportService;
    private final AnimalReportRepository animalReportRepository;

    private static final String MESSAGE = """
            (Рацион:)(\\s)(\\W+)(;)
            (Здоровье:)(\\s)(\\W+)(;)
            (Поведение:)(\\s)(\\W+)(;)""";


    @Override
    public void runAbout(@NotNull Customer customer) {
        String welcomeMessage = String.format("""
                        *Добро пожаловать, %s %s*\\!
                        Вас приветствует _*бот*_, который поможет сделать доброе дело\\.""",
                customer.getLastName(),
                customer.getFirstName());
        sendMessage(customer.getChatId(), welcomeMessage);
    }

    @Override
    public void runAdopt() {
    }

    @Override
    public void runCats(Long chatId, AnimalShelter shelter) {
        //Отправка картинки
        sendPhotoShelter(chatId, shelter);
        //отображение кнопок
        runDialogAnimalShelter(chatId);
    }

    @Override
    public void runDogs(Long chatId, AnimalShelter shelter) {

        //Отправка картинки
        sendPhotoShelter(chatId, shelter);
        //отображение кнопок
        runDialogAnimalShelter(chatId);
    }


    @Override
    public void runStart(Long chatId) {
        // Кнопки выбора приюта
        InlineKeyboardButton catShelterButton = new InlineKeyboardButton(CATS.getDescription());
        catShelterButton.callbackData(CATS.toString());

        InlineKeyboardButton dogShelterButton = new InlineKeyboardButton(DOGS.getDescription());
        dogShelterButton.callbackData(DOGS.toString());

        // Добавление кнопок в клавиатуру
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(catShelterButton, dogShelterButton);

        // Создание сообщения, добавление в него клавиатуры с рядом кнопок
        SendMessage sendMessage = new SendMessage(chatId, "*Выберите приют*");
        sendMessage.replyMarkup(inlineKeyboardMarkup);

        // Отправка сообщения
        prepareAndExecuteMessage(sendMessage);
    }


    @Override
    public void runInfo(Long chatId, AnimalShelter shelter) {
        String message = "<i>Кратко о приюте: </i>" + "<b>" + shelter.getDescription() + "</b><tg-emoji emoji-id=\"5368324170671202286\">\uD83D\uDC4D</tg-emoji>";
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);

        // Кнопки
        InlineKeyboardButton locationButton = new InlineKeyboardButton(LOCATION.getDescription());
        locationButton.callbackData(LOCATION.toString());

        InlineKeyboardButton contactButton = new InlineKeyboardButton(CONTACT.getDescription());
        contactButton.callbackData(CONTACT.toString());

        InlineKeyboardButton adviceButton = new InlineKeyboardButton(ADVICE.getDescription());
        adviceButton.callbackData(ADVICE.toString());

        // Добавление кнопок в клавиатуру
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup
                .addRow(locationButton)
                .addRow(contactButton)
                .addRow(adviceButton);

        // Создание сообщения, добавление в него клавиатуры с рядом кнопок
        SendMessage sendMessage1 = new SendMessage(chatId, "*Выберите дополнительное действие*");
        sendMessage1.replyMarkup(inlineKeyboardMarkup);

        // Отправка сообщения

        prepareAndExecuteMessage(sendMessage1);

    }
    /**
     * Загрузка отчета
     */
    @Override
    public void runReport(Long chatId, Update update) {
        String infoReport = """
                Для отчета нужна следующая информация:
                - Фото животного. \s
                - Рацион животного.
                - Общее самочувствие и привыкание к новому месту.
                - Изменение в поведении: отказ от старых привычек, приобретение новых.""";
        SendMessage sendMessage = new SendMessage(chatId, infoReport);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
        Pattern pattern = Pattern.compile(MESSAGE);
        Matcher matcher = pattern.matcher(update.message().caption());
        if (matcher.matches()) {
            String diet = matcher.group(3);
            String wellBeing = matcher.group(7);
            String behavior = matcher.group(11);
            Customer customer = new Customer();
            Animal animal = new Animal();

        GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
        GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            if (getFileResponse.isOk()) {
                File file = getFileResponse.file();
                file.fileSize();
                String pathPhoto = file.filePath();
                LocalDateTime dateTime = LocalDateTime.now();
                animalReportService.uploadAnimalReport(
                        pathPhoto
                        , diet
                        , wellBeing
                        , behavior
                        , dateTime
                        , animal
                        , customer);
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет принят!"));
                System.out.println("Отчет успешно принят от: " + update.message().chat().id());
            } else {
                throw new ReportException();
            }
        }
    }


    private final String CONGRATULATION = "Поздравляем!Вы прошли испытательный срок!";
    private final String WARNING = "Дорогой усыновитель, мы заметили, " +
            "что ты заполняешь отчет не так подробно," +
            " как необходимо. Пожалуйста, подойди ответственнее " +
            "к этому занятию. В противном случае волонтеры " +
            "приюта будут обязаны самолично проверять " +
            "условия содержания животного.";
    private final String ADDITION = "В отчете нет/нехватает информации, дополните описание! ";
    private final String REMINDER = "Вы уже отправляли отчет сегодня";


    public void checkingTheCorrectnessOfTheSentReport(Long chatId, Update update) {
        long chat = update.message().chat().id();
        long accountDaysInMonth = LocalDate.now().lengthOfMonth();
        long reportDay = animalReportRepository
                .findAll()
                .stream()
                .filter(s -> s.getCustomer().getChatId() == chat)
                .count() + 1;
        if (update.message() != null
                && update.message().photo() != null
                && update.message().caption() != null) {
            runReport(chatId, update);
        } else if
            (update.message() != null
                    && update.message().photo() != null
                    && update.message().caption() != null) {
                sendMessage(chat, REMINDER);
            }

        if (reportDay == accountDaysInMonth) {
            if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                sendMessage(chat, CONGRATULATION);
            }
        } else if (update.message() != null && update.message().photo() != null && update.message().caption() == null) {
            sendMessage(chat, ADDITION);
        }else if (update.message() == null && update.message().photo() != null && update.message().caption() == null) {
            sendMessage(chat, WARNING);
        }runReport(chatId ,update);
    }


    @Override
    public void runVolunteer(Long chatId) {

        Customer customer = customerRepository.findByChatId(chatId).get();

        //Отправка сообщения в чат с волонтерами
        String volunteerMessage = String.format("*%s* (@%s) зовёт волонтёра!", customer.getFirstName(), chatId);
        String escapedVolunteerMessage = volunteerMessage
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("!", "\\!");
        SendMessage sendVolunteerMessage = new SendMessage(telegramProperties.volunteerChatId(), escapedVolunteerMessage);
        prepareAndExecuteMessage(sendVolunteerMessage);

        //Отправка сообщения в чат с ботом
        String customerMessage = "Волонтёр скоро свяжется с Вами!";
        String escapedCustomerMessage = customerMessage
                .replace("!", "\\!");
        SendMessage sendCustomerMessage = new SendMessage(chatId, escapedCustomerMessage);
        prepareAndExecuteMessage(sendCustomerMessage);
    }

    @Override
    public void runContact(Long chatId, AnimalShelter shelter) {
        String message = "<i>Номер телефона приюта: </i><b>" + shelter.getContacts() + "</b>";
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void runAdvice(Long chatId, AnimalShelter shelter) {
        try {
            byte[] pdf = Files.readAllBytes(Paths.get(
                    Objects.requireNonNull(BotUpdatesListener.class.getResource(
                            "/" + shelter.getSafetyAdvice())).toURI()));
            SendDocument sendDocument = new SendDocument(chatId, pdf).fileName(shelter.getSafetyAdvice());

            sendDocument.caption(
                    "Рекомендации для будущих хозяев от " + shelter.getName() + " приюта!"
            );
            telegramBot.execute(sendDocument);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void runLocation(Long chatId, AnimalShelter shelter) {
        sendAddressAndWorkSchedule(chatId, shelter);
        try {
            byte[] photo = Files.readAllBytes(Paths.get(
                    BotUpdatesListener.class.getResource("/" + shelter.getDrivingDirections()).toURI()));
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            sendPhoto.caption(
                    "Схема проезда к " + shelter.getName() + " приюту!"
            );
            telegramBot.execute(sendPhoto);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    private void sendAddressAndWorkSchedule(Long chatId, AnimalShelter shelter) {
        String first = "<b>Расписание работы приюта, адрес и схема проезда.</b>";
        String second = "<i>Приют работает: <b><s>" + shelter.getWorkSchedule() + "</s></b></i>";
        String third = "<i>Наш адрес: <b>" + shelter.getAddress() + "</b></i>";
        SendMessage sendMessage = new SendMessage(chatId, first);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
        sendMessage = new SendMessage(chatId, second);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
        sendMessage = new SendMessage(chatId, third);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
    }


    @Override
    public void sendMessage(@NotNull Long chatId, String message) {
        prepareAndExecuteMessage(new SendMessage(chatId, message));
    }

    public void prepareAndExecuteMessage(SendMessage sendMessage) {
        sendMessage.parseMode(ParseMode.MarkdownV2);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Ошибка вывода сообщения: {}", sendResponse.description());
        }
    }

    private void runDialogAnimalShelter(Long chatId) {
        // Кнопки
        InlineKeyboardButton infoShelterButton = new InlineKeyboardButton(INFO.getDescription());
        infoShelterButton.callbackData(INFO.toString());

        InlineKeyboardButton adoptAnimalButton = new InlineKeyboardButton(ADOPT.getDescription());
        adoptAnimalButton.callbackData(ADOPT.toString());

        InlineKeyboardButton reportAnimalButton = new InlineKeyboardButton(REPORT.getDescription());
        reportAnimalButton.callbackData(REPORT.toString());

        InlineKeyboardButton volunteerButton = new InlineKeyboardButton(VOLUNTEER.getDescription());
        volunteerButton.callbackData(VOLUNTEER.toString());

        // Добавление кнопок в клавиатуру
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup
                .addRow(infoShelterButton)
                .addRow(adoptAnimalButton)
                .addRow(reportAnimalButton)
                .addRow(volunteerButton);

        // Создание сообщения, добавление в него клавиатуры с рядом кнопок
        SendMessage sendMessage = new SendMessage(chatId, "*Выберите действие*");
        sendMessage.replyMarkup(inlineKeyboardMarkup);

        // Отправка сообщения

        prepareAndExecuteMessage(sendMessage);
    }

    //Вспомогательный метод для отправки картинки при выборе приюта:
    private void sendPhotoShelter(Long chatId, AnimalShelter shelter) {
        try {
            byte[] photo = Files.readAllBytes(Paths.get(
                    BotUpdatesListener.class.getResource("/" + shelter.getImageName()).toURI()));
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            sendPhoto.caption(
                    "Приветствуем Вас в " + shelter.getName() + " приюте!"
            );
            telegramBot.execute(sendPhoto);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
