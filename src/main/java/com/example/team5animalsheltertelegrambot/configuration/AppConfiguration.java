package com.example.team5animalsheltertelegrambot.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.team5animalsheltertelegrambot")
@EntityScan("com.example.team5animalsheltertelegrambot.entity")
@ConfigurationPropertiesScan("com.example.team5animalsheltertelegrambot.properties")
public class AppConfiguration {
}
