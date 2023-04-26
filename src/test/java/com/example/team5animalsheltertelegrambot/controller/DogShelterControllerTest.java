package com.example.team5animalsheltertelegrambot.controller;

import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import com.example.team5animalsheltertelegrambot.repository.DogShelterRepository;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.team5animalsheltertelegrambot.constant.ShelterConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DogShelterControllerTest.class)
class DogShelterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShelterService<DogShelter> shelterService;

    @MockBean
    private DogShelterRepository dogShelterRepository;
    DogShelter dogShelterDefault;
    DogShelter dogShelterCorrect;


    @BeforeEach
    void init() {
        dogShelterDefault = new DogShelter();
        dogShelterDefault.setId(999);
        dogShelterDefault.setName(DEFAULT_NAME);
        dogShelterDefault.setContacts(DEFAULT_CONTACT);
        dogShelterDefault.setDescription(DEFAULT_DESCRIPTION);
        dogShelterDefault.setAddress(DEFAULT_ADDRESS);

        dogShelterCorrect = new DogShelter();
        dogShelterCorrect.setId(998);
        dogShelterCorrect.setName(CORRECT_NAME);
        dogShelterCorrect.setContacts(CORRECT_CONTACT);
        dogShelterCorrect.setDescription(CORRECT_DESCRIPTION);
        dogShelterCorrect.setAddress(CORRECT_ADDRESS);
    }

    @Test
    void create() throws Exception {
        when(dogShelterRepository.save(any(DogShelter.class))).thenReturn(dogShelterDefault);
        this.mockMvc.perform(post("/dogShelter/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dogShelterDefault)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(dogShelterDefault.getName())))
                .andExpect(jsonPath("$.contacts", is(dogShelterDefault.getContacts())))
                .andExpect(jsonPath("$.description", is(dogShelterDefault.getDescription())))
                .andExpect(jsonPath("$.address", is(dogShelterDefault.getAddress())));
    }

    @Test
    void setName() {
    }

    @Test
    void getName() {
    }

    @Test
    void updateName() {
    }

    @Test
    void setAddress() {
    }

    @Test
    void getAddress() {
    }

    @Test
    void updateAddress() {
    }

    @Test
    void setContact() {
    }

    @Test
    void getContact() {
    }

    @Test
    void updateContact() {
    }

    @Test
    void setDescription() {
    }

    @Test
    void getDescription() {
    }

    @Test
    void updateDescription() {
    }
}