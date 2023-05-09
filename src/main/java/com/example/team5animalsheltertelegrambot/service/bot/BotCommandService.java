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
     * Выводит информацию о том, как взять животное из приюта
     */
    void runAdopt(Long chatId, AnimalShelter shelter);

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
     * Запускает интерфейс для отчета
     */
    void runReport(Message message);

    void saveText(Message message);

    /**
     * Вызывает волонтера
     *
     * @param chatId идентификатор чата Телеграм
     */
    void runVolunteer(Long chatId);

    void runTelephone(Long chatId);

    void saveTelephone(long chatId, String phone);

    void sendMessageToVolunteer(Long chatId, String text);

    void runContact(Long chatId, AnimalShelter shelter);

    void runLocation(Long chatId, AnimalShelter shelter);

    /**
     * Выводит текстовое сообщение с форматированием в Markdown (обрезок телеграмовский)
     *
     * @param chatId  идентификатор чата Телеграм
     * @param message Строка сообщения в формате Markdown (см. доки бота)
     */
    void sendMessage(@NotNull Long chatId, String message);
}
