package com.example.team5animalsheltertelegrambot.controller;


import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.team5animalsheltertelegrambot.constant.ShelterConstants.DEFAULT_NAME;
import static org.hamcrest.CoreMatchers.is;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatShelterController.class)
class CatShelterControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CatShelterRepository catShelterRepository;
    CatShelter catShelterDefault;


    @BeforeEach
    void init() {
        catShelterDefault = new CatShelter();
        catShelterDefault.setId(1);
        catShelterDefault.setName(DEFAULT_NAME);

    }

    @Test
    void create() throws Exception {
        catShelterDefault = new CatShelter();
        catShelterDefault.setId(1);
        catShelterDefault.setName("DEFAULT_NAME");

        JSONObject object = new JSONObject();
        object.put("id", 1L);
        object.put("name", "DEFAULT_NAME");
        when(catShelterRepository.save(catShelterDefault)).thenReturn(catShelterDefault);

        this.mockMvc.perform(post("/catShelter/")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(object.toString()));

        verify(catShelterRepository).save(catShelterDefault);
    }

    @Test
    void getAll() throws Exception {
        List<CatShelter> expected = new ArrayList<>();
        expected.add(catShelterDefault);
        when(catShelterRepository.findAll()).thenReturn(expected);
        mockMvc.perform(get("/catShelter/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(expected.size())));
    }

    @Test
    void getShelter() throws Exception {
        when(catShelterRepository.findById(anyInt())).thenReturn(Optional.ofNullable(catShelterDefault));
        this.mockMvc.perform(get("/catShelter/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(catShelterDefault.getId())))
                .andExpect(jsonPath("$.name", is(catShelterDefault.getName())));
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(catShelterRepository).deleteById(anyInt());
        this.mockMvc.perform(delete("/catShelter/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
