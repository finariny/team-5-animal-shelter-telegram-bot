package com.example.team5animalsheltertelegrambot.service.bot;

import com.example.team5animalsheltertelegrambot.configuration.CommandType;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;

import javax.validation.constraints.NotNull;

/**
 * Сервис реализующий команды Телеграм бота
 * @see CommandType
 */
public interface BotCommandService {

    /**
     * Выводит информацию о программе
     *
     * @param customer пользователь бота
     */
    void runAbout(@NotNull Customer customer);

    /**
     * Выводит информацию как взять животное из приюта     *
     */
    void runAdopt();

    /**
     * Выводит клавиатуру с командами для работы с приютом для кошек
     *
     * @param chatId идентификатор чата Телеграм
     */
    void runCats(Long chatId);

    /**
     * Выводит клавиатуру с командами для работы с приютом для собак
     *
     * @param chatId  идентификатор чата Телеграм
     */
    void runDogs(Long chatId);

    /**
     * Стартует работу, предоставляет выбор приюта
     *
     * @param chatId  идентификатор чата Телеграм
     */
    void runStart(Long chatId);

    /**
     * Выводит информацию о приюте
     */
    void runInfo();

    /**
     * Запускает интерфейс для отчета
     */
    void runReport();

    /**
     * Вызывает волонтера
     */
    void runVolunteer();

    /**
     * Выводит текстовое сообщение с форматированием в Markdown (обрезок телеграмовский)
     *
     * @param chatId  идентификатор чата Телеграм
     * @param message Строка сообщения в формате Markdown (см. доки бота)
     */
    void sendMessage(@NotNull Long chatId, String message);
}
