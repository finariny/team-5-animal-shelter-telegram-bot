package com.example.team5animalsheltertelegrambot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Обрабатывает конфигурации с префиксом telegram
 *
 * @param token токен телеграм бота
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "telegram")
public record TelegramProperties(String token,
                                 String volunteerChatId) {
}
