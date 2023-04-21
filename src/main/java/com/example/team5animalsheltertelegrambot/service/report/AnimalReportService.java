package com.example.team5animalsheltertelegrambot.service.report;

import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.repository.AnimalReportRepository;
import com.pengrad.telegrambot.model.File;

import java.util.Date;
import java.util.List;


public interface AnimalReportService {

    /**
     * Загрузка отчета по определенным параметрам.
     * @param customerChatId
     * @param photoFile
     * @param file
     * @param diet
     * @param wellBeing
     * @param behavior
     * @param filePath
     * @param dateSendMessage
     * @param timeDate
     * @param reportDay
     */
    void uploadAnimalReport(Long customerChatId,
                            byte[] photoFile, File file,
                            String diet, String wellBeing,
                            String behavior, String filePath,
                            Date dateSendMessage,
                            Long timeDate,long reportDay);

    /**
     * Поиск отчета по id.
     * @param id
     * @return {@link AnimalReportRepository#findById(Object)}
     */
    AnimalReport findById(Integer id);

    /**
     * Метод для сохранения отчета.
     * @param report
     * @return {@link AnimalReportRepository#save(Object)}
     */
    AnimalReport save(AnimalReport report);

    /**
     * Метод для удаления отчета по идентификатору.
     *
     * @param id
     */
    void remove(Integer id);

    /**
     * Метод для получения всех отчетов.
     * @return {@link AnimalReportRepository#findAll()}
     */
    List<AnimalReport> getAll();

}
