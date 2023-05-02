package com.example.team5animalsheltertelegrambot.configuration;

import com.example.team5animalsheltertelegrambot.service.bot.BotCommandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * Перечисление создано для упорядочения команд Бота, а так же
 * для избежания ошибок и разных трактовок команд
 *
 * @see BotCommandService
 */
@Getter
@RequiredArgsConstructor
public enum CommandType {
    ABOUT("/about", "О программе"),
    ADOPT("/adopt", "Взять животное из приюта"),
    CATS("/cats", "Приют для кошек"),
    DOGS("/dogs", "Приют для собак"),
    START("/start", "Добро пожаловать!"),
    INFO("/info", "Информация о приюте"),
    REPORT("/report", "Отчет о животном"),
    VOLUNTEER("/volunteer", "Позвать волонтера"),
    SHELTER("/shelter", "О приюте"),
    LOCATION("/location","Расписание работы приюта, адрес и схема проезда"),
    CONTACT("/contact", "телефон для связи"),
    ADVICE("/advice","Как стать хозяином питомца. Рекомендации");


    private final String command;
    private final String description;

    /**
     * Возвращает экземпляр перечисления {@link CommandType} по id
     *
     * @param command команда для Телеграм бота
     * @return {@link CommandType}
     */
    @Nullable
    public static CommandType fromCommand(String command) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.getCommand().equals(command)) {
                return commandType;
            }
        }
        return null;
    }
}
