package com.example.team5animalsheltertelegrambot.service;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import com.example.team5animalsheltertelegrambot.service.shelter.impl.ShelterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.example.team5animalsheltertelegrambot.constant.ShelterConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {

    private List<Cat> cats = new ArrayList<>();
    private List<CatShelter> shelters = new ArrayList<>();
    private CatShelter catShelter = new CatShelter();



    @Mock
    private CatShelterRepository catShelterRepositoryMock;

    private ValidationRegularService validationRegularService = new ValidationRegularService();
    @InjectMocks
    private ShelterService out = new ShelterServiceImpl();



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
        assertEquals(CORRECT_CONTACT,out.updateContact(catShelter,CORRECT_CONTACT));
    }

    @Test
    void updateDescription() {
        assertEquals(CORRECT_DESCRIPTION,out.updateDescription(catShelter,CORRECT_DESCRIPTION));
    }



}
