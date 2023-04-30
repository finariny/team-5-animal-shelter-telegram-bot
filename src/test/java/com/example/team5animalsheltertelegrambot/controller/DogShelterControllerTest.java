package com.example.team5animalsheltertelegrambot.controller;

import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import com.example.team5animalsheltertelegrambot.repository.DogShelterRepository;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.json.JSONObject;
import org.springframework.test.web.servlet.ResultMatcher;

import static com.example.team5animalsheltertelegrambot.constant.ShelterConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DogShelterController.class)
class DogShelterControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private DogShelterRepository dogShelterRepository;
    DogShelter dogShelterDefault;


    @BeforeEach
    void init() {
        dogShelterDefault = new DogShelter();
        dogShelterDefault.setId(1);
        dogShelterDefault.setName(DEFAULT_NAME);

    }

    @Test
    void create() throws Exception {
        dogShelterDefault = new DogShelter();
        dogShelterDefault.setId(1);
        dogShelterDefault.setName("DEFAULT_NAME");

        JSONObject object = new JSONObject();
        object.put("id", 1L);
        object.put("name", "DEFAULT_NAME");
        when(dogShelterRepository.save(dogShelterDefault)).thenReturn(dogShelterDefault);

        this.mockMvc.perform(post("/dogShelter/")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(object.toString()));

        verify(dogShelterRepository).save(dogShelterDefault);
    }

    @Test
    void getAll() throws Exception {
        List<DogShelter> expected = new ArrayList<>();
        expected.add(dogShelterDefault);
        when(dogShelterRepository.findAll()).thenReturn(expected);
        mockMvc.perform(get("/dogShelter/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(expected.size())));
    }

    @Test
    void getShelter() throws Exception {
        when(dogShelterRepository.findById(anyInt())).thenReturn(Optional.ofNullable(dogShelterDefault));
        this.mockMvc.perform(get("/dogShelter/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dogShelterDefault.getId())))
                .andExpect(jsonPath("$.name", is(dogShelterDefault.getName())));
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(dogShelterRepository).deleteById(anyInt());
        this.mockMvc.perform(delete("/dogShelter/{id}", 1))
                .andExpect(status().isNoContent());
    }
}