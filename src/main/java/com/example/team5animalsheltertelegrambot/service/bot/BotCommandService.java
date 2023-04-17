package com.example.team5animalsheltertelegrambot.service.bot;

import com.example.team5animalsheltertelegrambot.entity.person.Customer;

import javax.validation.constraints.NotNull;

public interface BotCommandService {

    void runAbout(Customer newCustomer);

    void runAdopt();

    void runCats(Long chatId);

    void runDogs(Long chatId);

    void runStart(Long chatId);

    void runInfo();

    void runReport();

    void runVolunteer();

    void sendMessage(@NotNull Long chatId, String message);
}
