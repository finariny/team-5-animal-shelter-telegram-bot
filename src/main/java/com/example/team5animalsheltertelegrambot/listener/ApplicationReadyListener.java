package com.example.team5animalsheltertelegrambot.listener;

import com.example.team5animalsheltertelegrambot.service.bot.BotMenuService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SetMyCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Класс предназначен для описания некой логики после готовности приложения
 */
@Component
@RequiredArgsConstructor
public class ApplicationReadyListener {

    private final TelegramBot telegramBot;
    private final BotMenuService menuService;

    /**
     * Метод будет запущен после всех шагов инициализации по событию ApplicationReadyEvent,
     * источником которого является SpringApplication.
     * <p>
     * 1. добавляет стартовое меню к телеграм боту.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        System.out.println("Hello P.A.N.K. !!!");
        telegramBot.execute(new SetMyCommands(menuService.createMainMenuCommands()));
    }
}
