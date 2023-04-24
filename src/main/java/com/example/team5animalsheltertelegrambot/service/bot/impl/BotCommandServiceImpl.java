package com.example.team5animalsheltertelegrambot.service.bot.impl;

import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import com.example.team5animalsheltertelegrambot.listener.BotUpdatesListener;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
import com.example.team5animalsheltertelegrambot.repository.DogShelterRepository;
import com.example.team5animalsheltertelegrambot.service.bot.BotCommandService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.team5animalsheltertelegrambot.configuration.CommandType.*;

@Service
@RequiredArgsConstructor
public class BotCommandServiceImpl implements BotCommandService {
    private final Logger logger = LoggerFactory.getLogger(BotCommandServiceImpl.class);

    private final TelegramBot telegramBot;

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
    public void runCats(Long chatId,AnimalShelter shelter) {
        //Отправка картинки
        sendPhotoShelter(chatId, shelter);
        //отображение кнопок
        runDialogAnimalShelter(chatId);
    }

    @Override
    public void runDogs(Long chatId,AnimalShelter shelter) {

        //Отправка картинки
        sendPhotoShelter(chatId,shelter);
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
        String message = "Информация о приюте: "+ shelter.getDescription();
        SendMessage sendMessage = new SendMessage(chatId, message);
        prepareAndExecuteMessage(sendMessage);
    }

    @Override
    public void runReport() {

    }

    @Override
    public void runVolunteer() {

    }

    @Override
    public void runContact(Long chatId,AnimalShelter shelter){
        String message = "Номер телефона приюта: "+ shelter.getContacts();
        SendMessage sendMessage = new SendMessage(chatId, message);
        prepareAndExecuteMessage(sendMessage);
    }
    @Override
    public void runAdvice(Long chatId, AnimalShelter shelter){
        try {
            byte[] pdf = Files.readAllBytes(Paths.get(
                    BotUpdatesListener.class.getResource("/"+shelter.getSafetyAdvice()).toURI()));
            SendDocument sendDocument = new SendDocument(chatId, pdf).fileName(shelter.getSafetyAdvice());

            sendDocument.caption(
                    "Рекомендации для будущих хозяев от "+shelter.getName()+" приюта!"
            );
            telegramBot.execute(sendDocument);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void runLocation(Long chatId, AnimalShelter shelter) {
        try {
            byte[] photo = Files.readAllBytes(Paths.get(
                    BotUpdatesListener.class.getResource("/"+shelter.getDrivingDirections()).toURI()));
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            sendPhoto.caption(
                    "Схема проезда к "+shelter.getName()+" приюту!"
            );
            String string = "Расписание работы приюта, адрес и схема проезда:" + "\n" +
                    "Приют работает: " +
                    shelter.getWorkSchedule() + "\n" +
                    "Наш адрес: " +
                    shelter.getAddress();
            sendMessage(chatId, string);
            telegramBot.execute(sendPhoto);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
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
                    BotUpdatesListener.class.getResource(shelter.getImageName()/*"/catShelter.jpg"*/).toURI())); //TODO Проверить! - подтягивает ли фото в соответствии с полем шелтера из репозитория
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            sendPhoto.caption(
                    "Приветствуем Вас в "+/*">>PussyCat home<<"*/shelter.getName()+" приюте!" //TODO Проверить!  - подтягивает ли текущее название приюта
            );
            telegramBot.execute(sendPhoto);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
