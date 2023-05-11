package com.example.team5animalsheltertelegrambot.repository;

import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface AnimalReportRepository extends JpaRepository<AnimalReport, Integer> {

    /**
     * Поиск последнего отчета по питомцу
     *
     * @param animal Питомец (кошка или собака)
     * @return {@link AnimalReport} Последний отчет
     */
    @Transactional
    AnimalReport findFirstByAnimalOrderByDateCreateDesc(Animal animal);
}
