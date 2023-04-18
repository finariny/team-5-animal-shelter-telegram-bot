package com.example.team5animalsheltertelegrambot.service;

import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import java.util.Optional;

import static com.example.team5animalsheltertelegrambot.constant.AnimalConstants.CORRECT_CAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalRepository<Animal> animalRepositoryMock;

    @InjectMocks
    private AnimalService<Animal> out;

//    @Test
//    void save() {
//        when(animalRepositoryMock.save(CORRECT_CAT)).thenReturn(CORRECT_CAT);
//
//    }

    @Test
    @Transactional
    @Commit
    void findById() {
        when(animalRepositoryMock.save(CORRECT_CAT)).thenReturn(CORRECT_CAT);
        assertEquals(out.findAll().get(0), CORRECT_CAT);
    }

    @Test
    void findAllByHealth() {
    }

    @Test
    void findAllByVaccinate() {
    }

    @Test
    void findAll() {
    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }
}