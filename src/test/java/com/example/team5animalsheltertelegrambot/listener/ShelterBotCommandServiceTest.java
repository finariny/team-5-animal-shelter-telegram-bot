package com.example.team5animalsheltertelegrambot.listener;

import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.service.bot.impl.BotCommandServiceImpl;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static com.example.team5animalsheltertelegrambot.constant.ShelterConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShelterBotCommandServiceTest {
    @Mock
    private TelegramBot telegramBot;

    @Mock
    BotCommandServiceImpl botCommandService;

    @InjectMocks
    private BotUpdatesListener botUpdatesListener;


    private static String dataFilePath = "src\\test\\resources";


    CatShelter catShelterDefault;


    @BeforeEach
    void init() {
        catShelterDefault = new CatShelter();
        catShelterDefault.setId(1);
        catShelterDefault.setName(CORRECT_NAME);
        catShelterDefault.setContacts(CORRECT_CONTACT);
        catShelterDefault.setImageName(CORRECT_IMAGE);
        catShelterDefault.setAddress(CORRECT_ADDRESS);
        catShelterDefault.setDrivingDirections(CORRECT_IMAGE);

    }



    //проверка пути к файлу json
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = new File(dataFilePath+"/"+"callback_update.json");
        String test = String.valueOf(file.toURI());
        System.out.println(test);
    }

    @Test
    public void handleStartTest() throws URISyntaxException, IOException {
        File file = new File(dataFilePath+"/"+"start_update.json");
        String json = Files.readString(file.toPath());
        Update update = getUpdate(json, "/start");

        int actual = botUpdatesListener.process(Collections.singletonList(update));
        int expected = -1;
        assertEquals(expected,actual);

    }


    @Test
    void runCats() throws URISyntaxException, IOException  {
        File file = new File(dataFilePath+"/"+"callback_update.json");
        String json = Files.readString(file.toPath());
        Update update = getUpdate(json, "/cats");
        botUpdatesListener.process(Collections.singletonList(update));

        verify(botCommandService).runCats(1L,catShelterDefault);
//        Mockito.verify(mock.runCats(1L,catShelterDefault).sendPhotoShelter();
    }

    @Test
    void runDogs() {
    }

    @Test
    void runInfo() {
    }

    @Test
    void runContact() {
    }

    @Test
    void runAdvice() {
    }

    @Test
    void runLocation() {
    }

    private Update getUpdate(String json, String replaced){
        return BotUtils.fromJson(json.replace("%command%", replaced), Update.class);
    }
    private Update getUpdate(String json){
        return BotUtils.fromJson(json, Update.class);
    }

}