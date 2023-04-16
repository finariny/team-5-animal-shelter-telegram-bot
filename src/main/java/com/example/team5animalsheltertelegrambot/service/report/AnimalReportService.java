package com.example.team5animalsheltertelegrambot.service.report;

import com.example.team5animalsheltertelegrambot.entity.AnimalReport;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnimalReportService {
   /**
    * Загрузка отчета по определенным параметрам.
    * @param id
    * @param customer
    * @param pictureFile
    * @param file
    * @param diet
    * @param wellBeing
    * @param behavior
    * @param dateSendMessage
    * @param photo
    */
   public void uploadAnimalReport(Customer customer,
                                  String diet, String wellBeing,
                                  String behavior, LocalDateTime date,
                                  String photo);

   /**
    * Поиск отчета по id.
    *
    * @param id
    */
   public Optional<AnimalReport> findById(Integer id);

   /**
    * Метод для сохранения отчета.
    * @param report
    */
   public AnimalReport save(AnimalReport report);

   /**
    * Метод для удаления отчета по идентификатору.
    * @param id
    */
   public void remove(Integer id);

   /**
    * Метод для получения всех отчетов.
    */
   public List<AnimalReport> getAll();

}
