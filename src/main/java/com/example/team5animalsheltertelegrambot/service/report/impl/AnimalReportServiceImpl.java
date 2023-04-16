package com.example.team5animalsheltertelegrambot.service.report.impl;


import com.example.team5animalsheltertelegrambot.entity.AnimalReport;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.repository.AnimalReportRepository;
import com.example.team5animalsheltertelegrambot.service.report.AnimalReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с отчетами
 * @author Arutyunyan Aikanush
 */
@Service
public class AnimalReportServiceImpl implements AnimalReportService {

    private final AnimalReportRepository animalReportRepository;

    public AnimalReportServiceImpl(AnimalReportRepository animalReportRepository) {
        this.animalReportRepository = animalReportRepository;
    }

    @Override
    public void uploadAnimalReport(Customer customer,
                                   String diet,
                                   String wellBeing,
                                   String behavior,
                                   LocalDateTime date,
                                   String photo) {
        AnimalReport animalReport = new AnimalReport();
        animalReport.setCustomer(customer);
        animalReport.setDateTime(date);
        animalReport.setDiet(diet);
        animalReport.setPhoto(photo);
        animalReport.setWellBeing(wellBeing);
        animalReport.setBehavior(behavior);
        this.animalReportRepository.save(animalReport);
    }

    @Override
    public Optional<AnimalReport> findById(Integer id) {
        return this.animalReportRepository.findById(id);
    }

    @Override
    public AnimalReport save(AnimalReport report) {
        return this.animalReportRepository.save(report);
    }

    @Override
    public void remove(Integer id) {
        this.animalReportRepository.deleteById(id);
    }

    @Override
    public List<AnimalReport> getAll() {
        return this.animalReportRepository.findAll();
    }
}
