package com.example.team5animalsheltertelegrambot.service;

import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.repository.AnimalRepository;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import com.example.team5animalsheltertelegrambot.service.shelter.impl.ShelterServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.team5animalsheltertelegrambot.constant.AnimalConstants.CORRECT_CAT;
import static com.example.team5animalsheltertelegrambot.constant.ShelterConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {

    private List<Cat> cats = new ArrayList<>();
    private List<CatShelter> shelters = new ArrayList<>();
    private CatShelter catShelter = new CatShelter();



    @Mock
    private CatShelterRepository catShelterRepositoryMock;

    @InjectMocks
    private ShelterService out = new ShelterServiceImpl();


    @Test
    void findAllAnimalsInShelter(){
        when(catShelterRepositoryMock.findAll()).thenReturn(shelters);
       assertEquals(out.findAllAnimalsInShelter(catShelterRepositoryMock), shelters);
    }

    @Test
    void updateName() {
        assertEquals(CORRECT_NAME,out.updateName(catShelter,CORRECT_NAME));
    }

    @Test
    void updateAddress() {
        assertEquals(CORRECT_ADDRESS,out.updateName(catShelter,CORRECT_ADDRESS));
    }

    @Test
    void updateContact() {
        assertEquals(CORRECT_CONTACT,out.updateName(catShelter,CORRECT_CONTACT));
    }

    @Test
    void importSchemaDataFile() {


    }

    @Test
    void getSchemaDataFile() {
    }

    @Test
    void cleanDataFile() {
    }

    @Test
    void importAdviceDataFile() {
    }

    @Test
    void getAdviceDataFile() {
    }

}
