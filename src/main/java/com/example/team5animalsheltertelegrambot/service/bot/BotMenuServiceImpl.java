package com.example.team5animalsheltertelegrambot.service.bot;

import com.pengrad.telegrambot.model.BotCommand;
import org.springframework.stereotype.Service;

import static com.example.team5animalsheltertelegrambot.configuration.CommandType.CATS;
import static com.example.team5animalsheltertelegrambot.configuration.CommandType.DOGS;

@Service
public class BotMenuServiceImpl implements BotMenuService {

    @Override
    public BotCommand[] getStartCommands() {
        return new BotCommand[]{
                new BotCommand(CATS.getCommand(), CATS.getDescription()),
                new BotCommand(DOGS.getCommand(), DOGS.getDescription())
        };
    }
}
