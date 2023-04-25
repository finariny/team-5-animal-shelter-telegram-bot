package com.example.team5animalsheltertelegrambot.service.bot.impl;

import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import com.example.team5animalsheltertelegrambot.listener.BotUpdatesListener;
import com.example.team5animalsheltertelegrambot.properties.TelegramProperties;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
import com.example.team5animalsheltertelegrambot.repository.DogShelterRepository;
import com.example.team5animalsheltertelegrambot.repository.person.CustomerRepository;
import com.example.team5animalsheltertelegrambot.service.bot.BotCommandService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
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
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.example.team5animalsheltertelegrambot.configuration.CommandType.*;

@Service
@RequiredArgsConstructor
public class BotCommandServiceImpl implements BotCommandService {
    private final Logger logger = LoggerFactory.getLogger(BotCommandServiceImpl.class);

    private final TelegramBot telegramBot;

    private final TelegramProperties telegramProperties;

    private final CustomerRepository customerRepository;

    @Autowired
    private CatShelterRepository catShelterRepository;
    @Autowired
    private DogShelterRepository dogShelterRepository;

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
    public void runCats(Long chatId, Optional<AnimalShelter> shelter) {
        //Отправка картинки
        sendPhotoShelter(chatId, shelter);
        //отображение кнопок
        runDialogAnimalShelter(chatId);
    }

    @Override
    public void runDogs(Long chatId, Optional<AnimalShelter> shelter) {

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
    public void runInfo(Long chatId, Optional<AnimalShelter> shelter) {
        String message = "<i>Кратко о приюте: </i>" + "<b>" + shelter.get().getDescription() + "</b><tg-emoji emoji-id=\"5368324170671202286\">\uD83D\uDC4D</tg-emoji>";
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

    @Override
    public void runReport() {

    }

    /**
     * Загрузка фото
     */
    public void getPhoto (Message message){
        PhotoSize photoSize = message.photo()[message.photo().length - 1];
        GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
        if (getFileResponse.isOk()) {
            try {
                String extension = StringUtils.getFilenameExtension(
                        getFileResponse.file().filePath());
                byte[] photo = telegramBot.getFileContent(getFileResponse.file());
                Files.write(Paths.get(UUID.randomUUID() + "." + extension), photo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Метод для получения текста!
     */
    public void getMassage(Message message) {
        String text = message.text();
        GetFileResponse getFileResponse = telegramBot.execute(new GetFile(text));
        if (getFileResponse.isOk()) {
            String extension = StringUtils.getFilenameExtension(getFileResponse.file().filePath());
            try {
                String txt = telegramBot.getFullFilePath(getFileResponse.file());
                Files.write(Paths.get(UUID.randomUUID() + "." + extension), txt.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
    public void runContact(Long chatId, Optional<AnimalShelter> shelter) {
        String message = "<i>Номер телефона приюта: </i><b>" + shelter.get().getContacts() + "</b>";
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void runAdvice(Long chatId, Optional<AnimalShelter> shelter) {
        try {
            byte[] pdf = Files.readAllBytes(Paths.get(
                    Objects.requireNonNull(BotUpdatesListener.class.getResource(
                            "/" + shelter.get().getSafetyAdvice())).toURI()));
            SendDocument sendDocument = new SendDocument(chatId, pdf).fileName(shelter.get().getSafetyAdvice());

            sendDocument.caption(
                    "Рекомендации для будущих хозяев от " + shelter.get().getName() + " приюта!"
            );
            telegramBot.execute(sendDocument);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void runLocation(Long chatId, Optional<AnimalShelter> shelter) {
        sendAddressAndWorkSchedule(chatId, shelter);
        try {
            byte[] photo = Files.readAllBytes(Paths.get(
                    BotUpdatesListener.class.getResource("/" + shelter.get().getDrivingDirections()).toURI()));
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            sendPhoto.caption(
                    "Схема проезда к " + shelter.get().getName() + " приюту!"
            );
            telegramBot.execute(sendPhoto);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    private void sendAddressAndWorkSchedule(Long chatId, Optional<AnimalShelter> shelter) {
        String first = "<b>Расписание работы приюта, адрес и схема проезда.</b>";
        String second = "<i>Приют работает: <b><s>" + shelter.get().getWorkSchedule() + "</s></b></i>";
        String third = "<i>Наш адрес: <b>" + shelter.get().getAddress() + "</b></i>";
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
    private void sendPhotoShelter(Long chatId, Optional<AnimalShelter> shelter) {
        try {
            byte[] photo = Files.readAllBytes(Paths.get(
                    BotUpdatesListener.class.getResource("/" + shelter.get().getImageName()/*"/catShelter.jpg"*/).toURI()));
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            sendPhoto.caption(
                    "Приветствуем Вас в " + shelter.get().getName() + " приюте!"
            );
            telegramBot.execute(sendPhoto);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
