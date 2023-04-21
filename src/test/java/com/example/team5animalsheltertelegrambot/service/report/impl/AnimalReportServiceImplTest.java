package com.example.team5animalsheltertelegrambot.service.report.impl;

import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.repository.AnimalReportRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AnimalReportServiceImplTest {

    @Mock
    private AnimalReportRepository reportService;

    @InjectMocks
    private AnimalReportServiceImpl service;

    @Test
    void uploadAnimalReportTest() {
        AnimalReport expected = new AnimalReport();
        expected.setChatId(1L);
        expected.setFileSize(2L);
        expected.setDiet("diet");
        expected.setWellBeing("wellBeing");
        expected.setBehavior("behavior");
        expected.setPhoto("filePath");
        expected.setTimeDate(1L);
        expected.setReportDay(1L);

        Mockito.when(reportService.save(any(AnimalReport.class))).thenReturn(expected);
        AnimalReport actual = service.save(expected);

        Assertions.assertThat(actual.getChatId()).isEqualTo(expected.getChatId());
        Assertions.assertThat(actual.getFileSize()).isEqualTo(expected.getFileSize());
        Assertions.assertThat(actual.getDiet()).isEqualTo(expected.getDiet());
        Assertions.assertThat(actual.getWellBeing()).isEqualTo(expected.getWellBeing());
        Assertions.assertThat(actual.getBehavior()).isEqualTo(expected.getBehavior());
        Assertions.assertThat(actual.getPhoto()).isEqualTo(expected.getPhoto());
        Assertions.assertThat(actual.getReportDay()).isEqualTo(expected.getReportDay());
    }

    @Test
    void findByIdTest() {
        AnimalReport expected = new AnimalReport();
        expected.setChatId(1L);
        expected.setWellBeing("здоровье");
        expected.setBehavior("behaviorTest");
        expected.setDiet("dietTest");
        expected.setReportDay(2L);
        Mockito.when(reportService.findById(any(Integer.class))).thenReturn(Optional.of((expected)));

        AnimalReport actual = service.findById(1);

        Assertions.assertThat(actual.getChatId()).isEqualTo(expected.getChatId());
        Assertions.assertThat(actual.getWellBeing()).isEqualTo(expected.getWellBeing());
        Assertions.assertThat(actual.getBehavior()).isEqualTo(expected.getBehavior());
        Assertions.assertThat(actual.getDiet()).isEqualTo(expected.getDiet());
        Assertions.assertThat(actual.getReportDay()).isEqualTo(expected.getReportDay());
    }

    @Test
    void getAllTest() {
        List<AnimalReport> expected = new ArrayList<>();
        AnimalReport reportTest = new AnimalReport();
        reportTest.setChatId(1L);
        reportTest.setDiet("test");
        reportTest.setReportDay(3L);
        reportTest.setBehavior("test");
        reportTest.setPhoto("test");
        expected.add(reportTest);

        Mockito.when(reportService.findAll()).thenReturn(expected);
        Collection<AnimalReport> actual = reportService.findAll();

        Assertions.assertThat(actual.size()).isEqualTo(expected.size());
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void remove() {
    }
}