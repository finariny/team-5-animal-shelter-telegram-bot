package com.example.team5animalsheltertelegrambot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "telegram")
public record TelegramProperties(String token) {
}
