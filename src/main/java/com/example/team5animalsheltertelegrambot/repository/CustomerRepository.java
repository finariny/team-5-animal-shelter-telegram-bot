package com.example.team5animalsheltertelegrambot.repository;

import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с пользователями бота (посетители приюта)
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Проверяет наличие {@link Customer} по chatId.
     *
     * @param chatId идентификатор чата Телеграм.
     * @return {@literal true} если объект существует {@literal false} в противном случае.
     */
    Boolean existsByChatId(Long chatId);

    /**
     * Извлекает объект по идентификатору chatId.
     *
     * @param chatId идентификатор чата Телеграм.
     * @return сущность {@link Customer}
     */
    Customer findByChatId(Long chatId);
}
