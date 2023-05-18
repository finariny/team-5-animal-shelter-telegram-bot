package com.example.team5animalsheltertelegrambot.service.bot;

import com.example.team5animalsheltertelegrambot.configuration.CommandType;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import com.pengrad.telegrambot.model.Message;

import javax.validation.constraints.NotNull;

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
     * Отправляет PDF-файл о том, как взять животное из приюта
     *
     * @param chatId  идентификатор чата Телеграм
     * @param shelter приют для животных
     */
    void runAdopt(Long chatId, AnimalShelter shelter);

    /**
     * Выводит клавиатуру с командами для работы с приютом для кошек
     *
     * @param chatId  идентификатор чата Телеграм
     * @param shelter приют для животных
     */
    void runCats(Long chatId, AnimalShelter shelter);

    /**
     * Выводит клавиатуру с командами для работы с приютом для собак
     *
     * @param chatId  идентификатор чата Телеграм
     * @param shelter приют для животных
     */
    void runDogs(Long chatId, AnimalShelter shelter);

    /**
     * Стартует работу, предоставляет выбор приюта
     *
     * @param chatId идентификатор чата Телеграм
     */
    void runStart(Long chatId);

    /**
     * Выводит клавиатуру с командами для запроса "Узнать информацию о приюте"
     *
     * @param chatId  идентификатор чата Телеграм
     * @param shelter приют для животных
     */
    void runInfo(Long chatId, AnimalShelter shelter);

    /**
     * Выводит информацию о приюте
     *
     * @param chatId  идентификатор чата Телеграм
     * @param shelter приют для животных
     */
    void runShelter(Long chatId, AnimalShelter shelter);

    /**
     * Выводит контактные данные охраны для оформления пропуска на машину
     *
     * @param chatId  идентификатор чата Телеграм
     * @param shelter приют для животных
     */
    void runSecurity(Long chatId, AnimalShelter shelter);

    /**
     * Выводит общие рекомендации о технике безопасности на территории приюта
     *
     * @param chatId  идентификатор чата Телеграм
     * @param shelter приют для животных
     */
    void runSafety(Long chatId, AnimalShelter shelter);

    /**
     * Выводит интерфейс для отчета
     *
     * @param chatId идентификатор чата Телеграм
     */
    void runReport(Long chatId);

    /**
     * Сохраняет отчет о питомце
     *
     * @param message сообщение с отчетом
     */
    void saveReport(Message message);

    /**
     * Вызывает волонтера
     *
     * @param chatId идентификатор чата Телеграм
     */
    void runVolunteer(Long chatId);

    /**
     * Выводит сообщение с информацией о телефоне для связи
     *
     * @param chatId идентификатор чата Телеграм
     */
    void runTelephone(Long chatId);

    /**
     * Сохраняет номер телефона пользователя
     *
     * @param chatId идентификатор чата Телеграм
     * @param phone  номер телефона
     */
    void saveTelephone(long chatId, String phone);

    /**
     * Отправляет сообщение пользователя в чат с волонтерами
     *
     * @param chatId идентификатор чата Телеграм
     * @param text   сообщение для волонтеров
     */
    void sendMessageToVolunteer(Long chatId, String text);

    /**
     * Выводит сообщения с контактами приюта
     *
     * @param chatId  идентификатор чата Телеграм
     * @param shelter приют для животных
     */
    void runContact(Long chatId, AnimalShelter shelter);

    /**
     * Предоставляет информацию о локации приюта и схему проезда к ней
     *
     * @param chatId  идентификатор чата Телеграм
     * @param shelter приют для животных
     */
    void runLocation(Long chatId, AnimalShelter shelter);

    /**
     * Выводит текстовое сообщение с форматированием в Markdown (обрезок телеграмовский)
     *
     * @param chatId  идентификатор чата Телеграм
     * @param message Строка сообщения в формате Markdown (см. доки бота)
     */
    void sendMessage(@NotNull Long chatId, String message);
}
