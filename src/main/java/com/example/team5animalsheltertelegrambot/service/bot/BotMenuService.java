package com.example.team5animalsheltertelegrambot.service.bot;

import com.pengrad.telegrambot.model.BotCommand;

public interface BotMenuService {

    /**
     * Стартовое меню бота
     *
     * @return список команд стартового меню
     */
    public BotCommand[] createMainMenuCommands();
}
