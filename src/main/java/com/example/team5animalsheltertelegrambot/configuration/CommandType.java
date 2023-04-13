package com.example.team5animalsheltertelegrambot.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@RequiredArgsConstructor
public enum CommandType {
    CATS("/cats", "Приют для кошек"),
    DOGS("/dogs", "Приют для собак"),
    INFO("/info", "информация о приюте"),
    ADOPT("/adopt", "Взять животное из приюта"),
    REPORT("/report", "Отчет о животном"),
    VOLUNTEER("/volunteer", "Позвать волонтера");

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

    @Override
    public String toString() {
        return getDescription();
    }
}
