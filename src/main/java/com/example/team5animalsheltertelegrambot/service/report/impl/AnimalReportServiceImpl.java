package com.example.team5animalsheltertelegrambot.service.report.impl;


import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;

import com.example.team5animalsheltertelegrambot.exception.ReportException;
import com.example.team5animalsheltertelegrambot.repository.AnimalReportRepository;
import com.example.team5animalsheltertelegrambot.service.report.AnimalReportService;
import org.springframework.stereotype.Service;
import com.pengrad.telegrambot.model.File;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с отчетами
 *
 * @author Arutyunyan Aikanush
 */
@Service
public class AnimalReportServiceImpl implements AnimalReportService {

    private AnimalReportRepository animalReportRepository;

    public AnimalReportServiceImpl(AnimalReportRepository animalReportRepository) {
        this.animalReportRepository = animalReportRepository;
    }

    @Override
    public void uploadAnimalReport(Long customerChatId,
                                   byte[] photoFile, File file,
                                   String diet, String wellBeing,
                                   String behavior, String filePath,
                                   Date dateSendMessage,
                                   Long timeDate, long reportDay) {

        AnimalReport animalReport = new AnimalReport();
        animalReport.setChatId(customerChatId);
        animalReport.setPhotoFile(photoFile);
        animalReport.setFileSize(file.fileSize());
        animalReport.setDateTime(dateSendMessage);
        animalReport.setDiet(diet);
        animalReport.setWellBeing(wellBeing);
        animalReport.setBehavior(behavior);
        animalReport.setPhoto(filePath);
        animalReport.setTimeDate(timeDate);
        animalReport.setReportDay(reportDay);
        this.animalReportRepository.save(animalReport);
    }

    @Override
    public AnimalReport findById(Integer id) {
        AnimalReport animalReport = null;
        if (id == null) {
            System.out.println("Отчет с данным идентификатором не существует");
        } else {
            animalReport = this.animalReportRepository
                    .findById(id).orElseThrow(ReportException::new);
        }
        return animalReport;
    }

    @Override
    public AnimalReport save(AnimalReport report) {
        return this.animalReportRepository.save(report);
    }

    @Override
    public void remove(Integer id) {
        Optional<AnimalReport> byId = animalReportRepository.findById(id);
        if (byId.isPresent()) {
            this.animalReportRepository.deleteById(id);
        }
    }

    @Override
    public List<AnimalReport> getAll() {
        return this.animalReportRepository.findAll();
    }
}
