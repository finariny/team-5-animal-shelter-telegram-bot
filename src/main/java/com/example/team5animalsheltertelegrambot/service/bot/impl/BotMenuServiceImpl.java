package com.example.team5animalsheltertelegrambot.service.bot.impl;

import com.example.team5animalsheltertelegrambot.service.bot.BotMenuService;
import com.pengrad.telegrambot.model.BotCommand;
import org.springframework.stereotype.Service;

import static com.example.team5animalsheltertelegrambot.configuration.CommandType.ABOUT;
import static com.example.team5animalsheltertelegrambot.configuration.CommandType.START;

@Service
public class BotMenuServiceImpl implements BotMenuService {

    @Override
    public BotCommand[] createMainMenuCommands() {
        return new BotCommand[]{
                new BotCommand(START.getCommand(), START.getDescription()),
                new BotCommand(ABOUT.getCommand(), ABOUT.getDescription())
        };
    }
}
