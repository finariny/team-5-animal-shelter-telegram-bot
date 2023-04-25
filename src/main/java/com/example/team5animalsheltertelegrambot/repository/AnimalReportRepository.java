package com.example.team5animalsheltertelegrambot.repository;

import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalReportRepository extends JpaRepository<AnimalReport, Integer> {


}
