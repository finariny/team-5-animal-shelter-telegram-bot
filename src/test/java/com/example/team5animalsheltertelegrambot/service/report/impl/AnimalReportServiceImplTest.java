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
    private AnimalReportRepository animalReportRepositoryMock;

    @InjectMocks
    private AnimalReportServiceImpl service;

    @Test
    void uploadAnimalReportTest() {
        AnimalReport expected = new AnimalReport();
        expected.setPhoto("photo");
        expected.setDiet("diet");
        expected.setWellBeing("wellBeing");
        expected.setBehavior("behavior");

        Mockito.when(animalReportRepositoryMock.save(any(AnimalReport.class))).thenReturn(expected);
        AnimalReport actual = service.save(expected);

        Assertions.assertThat(actual.getDiet()).isEqualTo(expected.getDiet());
        Assertions.assertThat(actual.getWellBeing()).isEqualTo(expected.getWellBeing());
        Assertions.assertThat(actual.getBehavior()).isEqualTo(expected.getBehavior());
        Assertions.assertThat(actual.getPhoto()).isEqualTo(expected.getPhoto());

    }

    @Test
    void findByIdTest() {
        AnimalReport expected = new AnimalReport();
        expected.setPhoto("photo");
        expected.setWellBeing("здоровье");
        expected.setBehavior("behaviorTest");
        expected.setDiet("dietTest");
        Mockito.when(animalReportRepositoryMock.findById(any(Integer.class))).thenReturn(Optional.of(expected));

        AnimalReport actual = service.findById(1);

        Assertions.assertThat(actual.getPhoto()).isEqualTo(expected.getPhoto());
        Assertions.assertThat(actual.getWellBeing()).isEqualTo(expected.getWellBeing());
        Assertions.assertThat(actual.getBehavior()).isEqualTo(expected.getBehavior());
        Assertions.assertThat(actual.getDiet()).isEqualTo(expected.getDiet());

    }

    @Test
    void getAllTest() {
        List<AnimalReport> expected = new ArrayList<>();
        AnimalReport reportTest = new AnimalReport();

        reportTest.setPhoto("test");
        reportTest.setWellBeing("wellBeing");
        reportTest.setBehavior("test");
        reportTest.setDiet("test");
        expected.add(reportTest);

        Mockito.when(animalReportRepositoryMock.findAll()).thenReturn(expected);
        Collection<AnimalReport> actual = animalReportRepositoryMock.findAll();

        Assertions.assertThat(actual.size()).isEqualTo(expected.size());
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void remove() {
    }
}