package com.example.team5animalsheltertelegrambot.service.report.impl;


import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;

import com.example.team5animalsheltertelegrambot.exception.ReportException;
import com.example.team5animalsheltertelegrambot.repository.AnimalReportRepository;
import com.example.team5animalsheltertelegrambot.service.report.AnimalReportService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с отчетами
 *
 * @author Arutyunyan Aikanush
 */
@Service
public class AnimalReportServiceImpl implements AnimalReportService {

    private final AnimalReportRepository animalReportRepository;

    public AnimalReportServiceImpl(AnimalReportRepository animalReportRepository) {
        this.animalReportRepository = animalReportRepository;
    }

    @Override
    public void uploadAnimalReport(
             String photo
            , String diet
            , String wellBeing
            , String behavior
            , LocalDateTime dateCreate
            , Animal animal
            , Customer customer) {

        AnimalReport animalReport = new AnimalReport();
        animalReport.setPhoto(photo);
        animalReport.setDiet(diet);
        animalReport.setWellBeing(wellBeing);
        animalReport.setBehavior(behavior);
        animalReport.setDateCreate(dateCreate);
        animalReport.setAnimal(animal);
        animalReport.setCustomer(customer);
        this.animalReportRepository.save(animalReport);
    }

    @Override
    public AnimalReport findById(Integer id) {
        return this.animalReportRepository
                .findById(id).orElseThrow(ReportException::new);
    }

    @Override
    public AnimalReport save(AnimalReport report) {
        if (report != null) {
           this.animalReportRepository.save(report);
        }
        return report;
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
