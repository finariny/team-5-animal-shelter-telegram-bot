package com.example.team5animalsheltertelegrambot.service.bot.impl;

import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import com.example.team5animalsheltertelegrambot.exception.ReportException;
import com.example.team5animalsheltertelegrambot.listener.BotUpdatesListener;
import com.example.team5animalsheltertelegrambot.properties.TelegramProperties;
import com.example.team5animalsheltertelegrambot.repository.person.CustomerRepository;
import com.example.team5animalsheltertelegrambot.service.bot.BotCommandService;
import com.example.team5animalsheltertelegrambot.service.report.AnimalReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.team5animalsheltertelegrambot.configuration.CommandType.*;
import static com.example.team5animalsheltertelegrambot.service.ValidationRegularService.validateTelephone;

@Service
@RequiredArgsConstructor
public class BotCommandServiceImpl implements BotCommandService {

    @Value("${telegram.token}")
    private String token;
    private final Logger logger = LoggerFactory.getLogger(BotCommandServiceImpl.class);

    private final TelegramBot telegramBot;

    private final TelegramProperties telegramProperties;

    private final CustomerRepository customerRepository;

    //Константы сообщений для проверки reply сообщений с телефоном
    public static final String TELEPHONE = "Чтобы мы могли с Вами связаться, напишите в чат Ваш номер телефона.";
    public static final String PHONE_AGAIN = "Номер телефона не прошел проверку, пожалуйста, введите еще раз.";
    public static final String VOLUNTEER_MESSAGE = "Пожалуйста, напишите в чат по какому вопросу Вы обращаетесь.";

    private static final String MESSAGE = """
            (ID животного:)(\\s)(\\d+)(;)
            (Рацион:)(\\s+)([А-я\\d\\s.,!?:]+)(;)
            (Здоровье:)(\\s+)([А-я\\d\\s.,!?:]+)(;)
            (Поведение:)(\\s+)([А-я\\d\\s.,!?:]+)(;)""";

    private static final String exampleReport = """
            ID животного: 1; 
            Рацион: ваш текст;
            Здоровье: ваш текст;
            Поведение: ваш текст;""";

    private static final String infoReport = """        
            Для отчета нужна следующая информация:
            Фото животного
            ID животного
            Рацион
            Общее самочувствие и привыкание к новому месту
            Изменение в поведении: отказ от старых привычек, приобретение новых
            Скопируйте следующий пример. Не забудьте прикрепить фото!""";
    @Autowired
    private AnimalReportService animalReportService;

    @Override
    public void runAbout(@NotNull Customer customer) {
        String welcomeMessage = String.format("""
                        *Добро пожаловать, %s %s*\\!
                        Вас приветствует _*бот*_, который поможет сделать доброе дело\\.""",
                customer.getLastName(),
                customer.getFirstName());

        sendMessage(customer.getChatId(), welcomeMessage);
    }

    /**
     * Отправка Pdf файла с рекомендациями для будущего хозяина питомца
     */
    @Override
    public void runAdopt(Long chatId, AnimalShelter shelter) {
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

    /**
     * Вывод первого меню с кнопками выбора приюта кошек или собак
     */
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

    /**
     * Вывод отображения и меню приюта кошек
     */
    @Override
    public void runCats(Long chatId, AnimalShelter shelter) {
        //Отправка картинки
        sendPhotoShelter(chatId, shelter);
        //отображение кнопок
        runDialogAnimalShelter(chatId);
    }

    /**
     * Вывод отображения и меню приюта собак
     */
    @Override
    public void runDogs(Long chatId, AnimalShelter shelter) {

        //Отправка картинки
        sendPhotoShelter(chatId, shelter);
        //отображение кнопок
        runDialogAnimalShelter(chatId);
    }

    @Override
    public void runInfo(Long chatId, AnimalShelter shelter) {
        // Кнопки
        InlineKeyboardButton shelterButton = new InlineKeyboardButton(SHELTER.getDescription());
        shelterButton.callbackData(SHELTER.toString());

        InlineKeyboardButton locationButton = new InlineKeyboardButton(LOCATION.getDescription());
        locationButton.callbackData(LOCATION.toString());

        InlineKeyboardButton contactButton = new InlineKeyboardButton(CONTACT.getDescription());
        contactButton.callbackData(CONTACT.toString());

        InlineKeyboardButton securityButton = new InlineKeyboardButton(SECURITY.getDescription());
        securityButton.callbackData(SECURITY.toString());

        InlineKeyboardButton safetyButton = new InlineKeyboardButton(SAFETY.getDescription());
        safetyButton.callbackData(SAFETY.toString());

        // Добавление кнопок в клавиатуру
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup
                .addRow(shelterButton)
                .addRow(locationButton)
                .addRow(contactButton)
                .addRow(securityButton)
                .addRow(safetyButton);

        // Создание сообщения, добавление в него клавиатуры с рядом кнопок
        SendMessage sendMessage = new SendMessage(chatId, "*Выберите дополнительное действие*");
        sendMessage.replyMarkup(inlineKeyboardMarkup);

        // Отправка сообщения
        prepareAndExecuteMessage(sendMessage);
    }

    @Override
    public void runShelter(Long chatId, AnimalShelter shelter) {
        String message = "<i>Кратко о приюте: </i>" + "<b>" + shelter.getDescription() + " </b><tg-emoji emoji-id=\"5368324170671202286\">\uD83D\uDC4D</tg-emoji>";
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void runSecurity(Long chatId, AnimalShelter shelter) {
        String message = String.format("""
                        <b>Контактные данные охраны приюта "%s":</b>
                        %s""",
                shelter.getName(),
                shelter.getSecurityContact());
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void runSafety(Long chatId, AnimalShelter shelter) {
        try {
            byte[] pdf = Files.readAllBytes(Paths.get(
                    Objects.requireNonNull(BotUpdatesListener.class.getResource(
                            "/" + shelter.getShelterSafetyAdvice())).toURI()));
            SendDocument sendDocument = new SendDocument(chatId, pdf).fileName(shelter.getShelterSafetyAdvice());

            sendDocument.caption(
                    "Общие рекомендации о технике безопасности в приюте <b>\"" + shelter.getName() + "\"</b>!"
            );
            sendDocument.parseMode(ParseMode.HTML);
            telegramBot.execute(sendDocument);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Информационное меню для отправки отчета
     */
    @Override
    public void runReport(Message message) {
        Long chatId = message.chat().id();
        SendMessage sendMessage = new SendMessage(chatId, infoReport);
        telegramBot.execute(sendMessage);
        SendMessage sendMessage1 = new SendMessage(chatId, exampleReport);
        telegramBot.execute(sendMessage1);
    }

    @Override
    public void saveReport(Message message) {
        Long chatId = message.chat().id();
        String text = message.caption();

        Pattern pattern = Pattern.compile(MESSAGE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) {
            int id = Integer.parseInt(matcher.group(3));
            String diet = matcher.group(7);
            String wellBeing = matcher.group(11);
            String behavior = matcher.group(15);

            GetFile getFileRequest = new GetFile(message.photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                file.fileSize();
                String fullPathPhoto = file.filePath();
                LocalDateTime dateTime = LocalDateTime.now();

                Customer customer = customerRepository.findByChatId(chatId).orElseThrow();
                Animal animal = new Animal();

                animal.setId(id);
                AnimalReport animalReport = new AnimalReport();

                animalReport.setPhoto(fullPathPhoto);
                animalReport.setDiet(diet);
                animalReport.setWellBeing(wellBeing);
                animalReport.setBehavior(behavior);
                animalReport.setDateCreate(dateTime);
                animalReport.setAnimal(animal);
                animalReport.setCustomer(customer);

                animalReportService.save(animalReport);

                telegramBot.execute(new SendMessage(message.chat().id(), "Отчет успешно принят!"));
            } catch (Exception e) {
                telegramBot.execute(new SendMessage(
                        message.chat().id(), "Загрузка не удалась! Проверьте введенные данные!"));
            }
        } else {
            throw new ReportException();
        }
    }


    /**
     * Обработка нажатия кнопки "позвать Волонтера". Запрос на сообщение для волонтеров
     */
    @Override
    public void runVolunteer(Long chatId) {
        //Отправка сообщения в чат с ботом
        String customerMessage = VOLUNTEER_MESSAGE;
        SendMessage sendMessage = new SendMessage(chatId, customerMessage);
        sendMessage.replyMarkup(new ForceReply());
        telegramBot.execute(sendMessage);
    }

    /**
     * Обработка нажатия кнопки "Оставить номер телефона". Запрос на ответное сообщение с телефоном
     */
    @Override
    public void runTelephone(Long chatId) {
        //Отправка сообщения в чат с ботом
        SendMessage sendMessage = new SendMessage(chatId, TELEPHONE);
        sendMessage.replyMarkup(new ForceReply()); // новый диалог
        telegramBot.execute(sendMessage);
    }

    @Override
    public void saveTelephone(long chatId, String phone) {
        Customer customer = customerRepository.findByChatId(chatId).orElseThrow();

        if (validateTelephone(phone)) {
            customer.setPhone(phone);
            customerRepository.save(customer);
            SendMessage sendMessage = new SendMessage(chatId, "Номер телефона принят!");
            telegramBot.execute(sendMessage);
        } else {
            SendMessage sendMessage = new SendMessage(chatId, PHONE_AGAIN);
            sendMessage.replyMarkup(new ForceReply()); // новый диалог "Телефон заново!"
            telegramBot.execute(sendMessage);
        }
    }

    /**
     * Метод отправляющий сообщение в чат волонтеров
     */
    @Override
    public void sendMessageToVolunteer(Long chatId, String text) {
        Customer customer = customerRepository.findByChatId(chatId).get();

        //Отправка сообщения в чат с волонтерами
        String volunteerMessage = String.format("""
                        <b>%s</b> (@%s) зовёт волонтёра!
                        Номер телефона: %s.
                        Прикрепленное сообщение: %s""",
                customer.getFirstName(), chatId, customer.getPhone(), text);
        SendMessage sendVolunteerMessage = new SendMessage(telegramProperties.volunteerChatId(), volunteerMessage);
        sendVolunteerMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendVolunteerMessage);
    }

    /**
     * Отправка сообщения с контактами приюта
     */
    @Override
    public void runContact(Long chatId, AnimalShelter shelter) {
        String message = "<i>Номер телефона приюта: </i><b>" + shelter.getContacts() + "</b>";
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
    }

    /**
     * Предоставление информации о локации приюта и схемы проезда к ней
     */
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

    /**
     * Внутренний метод для отправки сообщений с информацией об адресе приюта (и другой информацией)
     */
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

    /**
     * Создание и отправка нового меню с кнопками, после выбора приюта кошек или собак
     */
    private void runDialogAnimalShelter(Long chatId) {
        // Кнопки
        InlineKeyboardButton infoShelterButton = new InlineKeyboardButton(INFO.getDescription());
        infoShelterButton.callbackData(INFO.toString());

        InlineKeyboardButton adoptButton = new InlineKeyboardButton(ADOPT.getDescription());
        adoptButton.callbackData(ADOPT.toString());

        InlineKeyboardButton reportAnimalButton = new InlineKeyboardButton(REPORT.getDescription());
        reportAnimalButton.callbackData(REPORT.toString());

        InlineKeyboardButton getUserPhoneButton = new InlineKeyboardButton(PHONE.getDescription());
        getUserPhoneButton.callbackData(PHONE.toString());

        InlineKeyboardButton volunteerButton = new InlineKeyboardButton(VOLUNTEER.getDescription());
        volunteerButton.callbackData(VOLUNTEER.toString());

        // Добавление кнопок в клавиатуру
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup
                .addRow(infoShelterButton)
                .addRow(adoptButton)
                .addRow(reportAnimalButton)
                .addRow(getUserPhoneButton)
                .addRow(volunteerButton);

        // Создание сообщения, добавление в него клавиатуры с рядом кнопок
        SendMessage sendMessage = new SendMessage(chatId, "*Выберите действие*");
        sendMessage.replyMarkup(inlineKeyboardMarkup);

        // Отправка сообщения
        prepareAndExecuteMessage(sendMessage);
    }

    /**
     * Вспомогательный метод для отправки картинки при выборе приюта:
     */
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
