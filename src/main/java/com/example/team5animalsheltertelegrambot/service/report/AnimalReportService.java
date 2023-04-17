package com.example.team5animalsheltertelegrambot.service.report;

import com.example.team5animalsheltertelegrambot.entity.AnimalReport;
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
   public void uploadAnimalReport(Customer customer,
                                  String diet, String wellBeing,
                                  String behavior, LocalDateTime date,
                                  String photo);

   /**
    * Поиск отчета по id.
    *@return {@link AnimalReportRepository#findById(Object)}
    * @param id
    */
   public Optional<AnimalReport> findById(Integer id);

   /**
    * Метод для сохранения отчета.
    * @param report
    * @return {@link AnimalReportRepository#save(Object)}
    */
   public AnimalReport save(AnimalReport report);

   /**
    * Метод для удаления отчета по идентификатору.
    * @param id
    */
   public void remove(Integer id);

   /**
    * Метод для получения всех отчетов.
    * @return {@link AnimalReportRepository#findAll()}
    */
   public List<AnimalReport> getAll();

}
