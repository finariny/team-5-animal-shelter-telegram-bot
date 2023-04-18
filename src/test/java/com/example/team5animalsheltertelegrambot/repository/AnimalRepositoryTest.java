package com.example.team5animalsheltertelegrambot.repository;

import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

import static com.example.team5animalsheltertelegrambot.constant.AnimalConstants.CORRECT_CAT;
import static org.junit.jupiter.api.Assertions.*;

class AnimalRepositoryTest {
    private AnimalRepository<Animal> animalRepository;

    @Test
    @Transactional
    void updateById() {
        Cat cat = new Cat("Musya", 2, false, false);
        animalRepository.save(cat);
        assertEquals(1, animalRepository.updateById(1, "Glafira", 3, true, false));
    }

    @Test
    void findAllByHealth() {
    }

    @Test
    void findAllByVaccination() {
    }
}