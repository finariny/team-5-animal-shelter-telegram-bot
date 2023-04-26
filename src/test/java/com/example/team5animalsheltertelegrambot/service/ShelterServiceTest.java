package com.example.team5animalsheltertelegrambot.service;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;

import com.example.team5animalsheltertelegrambot.service.shelter.impl.ShelterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.team5animalsheltertelegrambot.constant.ShelterConstants.*;
import static org.junit.jupiter.api.Assertions.*;



public class ShelterServiceTest {

    private final List<Cat> cats = new ArrayList<>();
    private final List<CatShelter> shelters = new ArrayList<>();
    private CatShelter catShelter=new CatShelter();;



    private final ShelterServiceImpl out = new ShelterServiceImpl();


    @Test
    void updateName() {
        assertEquals(CORRECT_NAME,out.updateName(catShelter,CORRECT_NAME));
    }

    @Test
    void updateNameFalse() {
        assertNotEquals(CORRECT_NAME,out.updateName(catShelter,DEFAULT_NAME));
    }

    @Test
    void updateAddress() {
        assertEquals(CORRECT_ADDRESS,out.updateName(catShelter,CORRECT_ADDRESS));
    }
    @Test
    void updateAddressFalse() {
        assertNotEquals(CORRECT_ADDRESS,out.updateName(catShelter,DEFAULT_ADDRESS));
    }

    @Test
    void updateContact() {
        assertEquals(CORRECT_CONTACT,out.updateContact(catShelter,CORRECT_CONTACT));
    }
    @Test
    void updateContactFalse() {
        assertNotEquals(CORRECT_CONTACT,out.updateContact(catShelter,DEFAULT_CONTACT));
    }

    @Test
    void updateDescription() {
        assertEquals(CORRECT_DESCRIPTION,out.updateDescription(catShelter,CORRECT_DESCRIPTION));
    }

    @Test
    void updateDescriptionFalse() {
        assertNotEquals(CORRECT_DESCRIPTION,out.updateDescription(catShelter,DEFAULT_DESCRIPTION));
    }



}
