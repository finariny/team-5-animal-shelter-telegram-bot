package com.example.team5animalsheltertelegrambot.configuration;

import com.example.team5animalsheltertelegrambot.properties.TelegramProperties;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TelegramConfiguration {

    private final TelegramProperties telegramProperties;

    /**
     * Создает {@link Bean} получив токен из файла свойств
     *
     * @return {@link Bean} очищенный от команд Телеграм Бот
     */
    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(telegramProperties.token());
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}
