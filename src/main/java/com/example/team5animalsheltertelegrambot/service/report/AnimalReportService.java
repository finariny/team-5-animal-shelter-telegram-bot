package com.example.team5animalsheltertelegrambot.service.report;

import com.example.team5animalsheltertelegrambot.entity.AnimalReport;
import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.repository.AnimalReportRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnimalReportService {
    /**
     * Загрузка отчета по определенным параметрам.
     * @param customer
     * @param diet
     * @param wellBeing
     * @param behavior
     * @param date
     * @param photo
     */
    void uploadAnimalReport(Customer customer,
                            String diet, String wellBeing,
                            String behavior, LocalDateTime date,
                            String photo, Animal animal);

    /**
     * Поиск отчета по id.
     * @param id
     * @return {@link AnimalReportRepository#findById(Object)}
     */
    Optional<AnimalReport> findById(Integer id);

    /**
     * Метод для сохранения отчета.
     * @param report
     * @return {@link AnimalReportRepository#save(Object)}
     */
    AnimalReport save(AnimalReport report);

    /**
     * Метод для удаления отчета по идентификатору.
     * @param id
     */
    Boolean remove(Integer id);

    /**
     * Метод для получения всех отчетов.
     * @return {@link AnimalReportRepository#findAll()}
     */
    List<AnimalReport> getAll();

}
