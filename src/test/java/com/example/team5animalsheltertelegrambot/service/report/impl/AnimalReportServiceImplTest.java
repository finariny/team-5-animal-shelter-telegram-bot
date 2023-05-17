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

import static org.mockito.Mockito.*;

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

    AnimalReport animalReport() {
        AnimalReport expected = new AnimalReport();
        expected.setPhoto("photo");
        expected.setDiet("diet");
        expected.setWellBeing("wellBeing");
        expected.setBehavior("behavior");
        return expected;
    }

    @Test
    void uploadAnimalReportTest() {
        Mockito.when(animalReportRepositoryMock.save(any(AnimalReport.class))).thenReturn(animalReport());
        AnimalReport actual = service.save(animalReport());
        Assertions.assertThat(actual.getDiet()).isEqualTo(animalReport().getDiet());
        Assertions.assertThat(actual.getWellBeing()).isEqualTo(animalReport().getWellBeing());
        Assertions.assertThat(actual.getBehavior()).isEqualTo(animalReport().getBehavior());
        Assertions.assertThat(actual.getPhoto()).isEqualTo(animalReport().getPhoto());

    }

    @Test
    void findByIdTest() {
        Mockito.when(animalReportRepositoryMock.findById(any(Integer.class))).thenReturn(Optional.of(animalReport()));
        AnimalReport actual = service.findById(1);
        Assertions.assertThat(actual.getPhoto()).isEqualTo(animalReport().getPhoto());
        Assertions.assertThat(actual.getWellBeing()).isEqualTo(animalReport().getWellBeing());
        Assertions.assertThat(actual.getBehavior()).isEqualTo(animalReport().getBehavior());
        Assertions.assertThat(actual.getDiet()).isEqualTo(animalReport().getDiet());
    }

    @Test
    void getAllTest() {
        List<AnimalReport> expected = new ArrayList<>();
        expected.add(animalReport());
        Mockito.when(animalReportRepositoryMock.findAll()).thenReturn(expected);
        Collection<AnimalReport> actual = animalReportRepositoryMock.findAll();
        Assertions.assertThat(actual.size()).isEqualTo(expected.size());
        Assertions.assertThat(actual).isEqualTo(expected);
    }


    @Test
    void remove() {
        Mockito.when(animalReportRepositoryMock.findById(any(Integer.class))).thenReturn(Optional.of(animalReport()));
        doNothing().when(animalReportRepositoryMock).deleteById(anyInt());
        service.remove(1);
        verify(animalReportRepositoryMock, times(1)).deleteById(anyInt());
    }
}