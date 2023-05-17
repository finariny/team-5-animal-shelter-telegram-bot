package com.example.team5animalsheltertelegrambot.repository.person;

import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

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
     * @return Объект-контейнер сущности {@link Customer}
     */
    Optional<Customer> findByChatId(Long chatId);
}
