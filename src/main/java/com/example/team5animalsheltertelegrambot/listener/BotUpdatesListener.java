package com.example.team5animalsheltertelegrambot.listener;

import com.example.team5animalsheltertelegrambot.configuration.CommandType;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
import com.example.team5animalsheltertelegrambot.repository.CustomerRepository;
import com.example.team5animalsheltertelegrambot.service.bot.BotCommandService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Основной класс для работы с Телеграм.
 * Реализует интерфейс {@link UpdatesListener} для обработки обратного вызова с доступными обновлениями
 *
 */
@Service
@RequiredArgsConstructor
public class BotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(BotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final CustomerRepository customerRepository;


    private final BotCommandService botCommandService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    /**
     * Метод обратного вызова, вызываемый при обновлениях.
     * Под обновлением подразумевается действие, совершённое с ботом — например, получение сообщения от пользователя.
     * Метод обрабатывает полученные обновления и в зависимости от их типа (сообщение или обратный запрос от событий нажатия кнопок),
     * отправляет на соответствующие обработчики
     *
     * @param updates доступные обновления
     * @return {@code UpdatesListener.CONFIRMED_UPDATES_ALL = -1}
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.debug("Обработка обновления: {}", update);
            if (update.callbackQuery() != null) {
                handleCallback(update.callbackQuery());
            }
            if (update.message() != null) {
                handleMessage(update.message());
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Обрабатывает обратные запросы от событий нажатия кнопок и
     * вызывает соответствующую команду
     *
     * @param callbackQuery {@link CallbackQuery} из {@link Update}
     */
    private void handleCallback(CallbackQuery callbackQuery) {
        String callbackQueryData = callbackQuery.data();
        Long chatId = callbackQuery.from().id();
        CommandType commandType = CommandType.valueOf(callbackQueryData);
        try {
            switch (commandType) {
                case CATS -> botCommandService.runCats(chatId);
                case DOGS -> botCommandService.runDogs(chatId);
            }
        } catch (Exception e) {
            logger.error("Ошибка обработки обратного вызова: {}", e.getMessage());
        }
    }

    /**
     * Обрабатывает сообщения и вызывает соответствующую команду.
     * При первом старте проверяет наличие регистрации пользователя в базе,
     * если его еще нет, то регистрирует пользователя и выводит пользователю информацию о Боте
     *
     * @param message сообщение из {@link Update}
     */
    private void handleMessage(Message message) {
        boolean isNewCustomer = false;
        try {
            Long chatId = message.from().id();
            Customer customer;

            if (customerRepository.existsByChatId(chatId)) {
                customer = customerRepository.findByChatId(chatId);
            } else {
                isNewCustomer = true;
                customer = customerRepository.save(
                        new Customer(message.from().firstName(), message.from().lastName(), chatId)
                );
            }

            String command = message.text();
            CommandType commandType = CommandType.fromCommand(command);
            if (commandType == null) {
                botCommandService.sendMessage(chatId, String.format("Команда *%s* не найдена", command));
            } else {
                switch (commandType) {
                    case ABOUT -> botCommandService.runAbout(customer);
                    case ADOPT -> botCommandService.runAdopt();
                    case CATS -> botCommandService.runCats(chatId);
                    case DOGS -> botCommandService.runDogs(chatId);
                    case START -> {
                        if (isNewCustomer) {
                            botCommandService.runAbout(customer);
                        }
                        botCommandService.runStart(chatId);
                    }
                    case INFO -> botCommandService.runInfo();
                    case REPORT -> botCommandService.runReport();
                    case VOLUNTEER -> botCommandService.runVolunteer();
                }
            }
        } catch (Exception e) {
            logger.error("Ошибка при обработке сообщения: {}", e.getMessage());
        }
    }
}
