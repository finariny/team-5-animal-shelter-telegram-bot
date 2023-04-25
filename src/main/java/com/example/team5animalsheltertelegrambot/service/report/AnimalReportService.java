package com.example.team5animalsheltertelegrambot.service.report;

import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.repository.AnimalReportRepository;

import java.time.LocalDateTime;

import java.util.List;


public interface AnimalReportService {

    /**
     * Загрузка отчета по определенным параметрам.
     * @param photo фото животного
     * @param diet рацион животного
     * @param wellBeing здоровье
     * @param behavior привычки
     * @param dateCreate дата создания
     * @param animal животное взятое из приюта
     * @param customer хозяин приютившего животное
     */
    void uploadAnimalReport(
            String photo
            , String diet
            , String wellBeing
            , String behavior
            , LocalDateTime dateCreate
            , Animal animal
            , Customer customer);


    /**
     * Поиск отчета по id.
     * @param id идентификатор
     * @return {@link AnimalReportRepository#findById(Object)}
     */
    AnimalReport findById(Integer id);

    /**
     * Метод для сохранения отчета.
     * @param report отчет
     * @return {@link AnimalReportRepository#save(Object)}
     */
    AnimalReport save(AnimalReport report);

    /**
     * Метод для удаления отчета по идентификатору.
     * @param id идентификатор
     */
    void remove(Integer id);

    /**
     * Метод для получения всех отчетов.
     * @return {@link AnimalReportRepository#findAll()}
     */
    List<AnimalReport> getAll();
}
