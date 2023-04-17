package com.example.team5animalsheltertelegrambot.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan("com.example.team5animalsheltertelegrambot")
@EntityScan("com.example.team5animalsheltertelegrambot.entity")
@ConfigurationPropertiesScan("com.example.team5animalsheltertelegrambot.properties")
public class AppConfiguration {
}
