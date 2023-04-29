package com.example.team5animalsheltertelegrambot.service.bot;

import com.example.team5animalsheltertelegrambot.configuration.CommandType;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Сервис реализующий команды Телеграм бота
 *
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
    void runCats(Long chatId, AnimalShelter shelter);

    /**
     * Выводит клавиатуру с командами для работы с приютом для собак
     *
     * @param chatId идентификатор чата Телеграм
     */
    void runDogs(Long chatId, AnimalShelter shelter);

    /**
     * Стартует работу, предоставляет выбор приюта
     *
     * @param chatId идентификатор чата Телеграм
     */
    void runStart(Long chatId);

    /**
     * Выводит информацию о приюте
     */
    void runInfo(Long chatId,AnimalShelter  shelter);

    /**
     * Запускает интерфейс для отчета
     */
    void runReport(Update update);

    /**
     * Вызывает волонтера
     *
     * @param chatId идентификатор чата Телеграм
     */
    void runVolunteer(Long chatId);

    void runContact(Long chatId, AnimalShelter  shelter);
    void runAdvice(Long chatId, AnimalShelter  shelter);

    void runLocation(Long chatId, AnimalShelter shelter);

    /**
     * Выводит текстовое сообщение с форматированием в Markdown (обрезок телеграмовский)
     *
     * @param chatId  идентификатор чата Телеграм
     * @param message Строка сообщения в формате Markdown (см. доки бота)
     */
    void sendMessage(@NotNull Long chatId, String message);
}
