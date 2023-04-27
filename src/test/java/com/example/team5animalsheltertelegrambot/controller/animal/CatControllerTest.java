package com.example.team5animalsheltertelegrambot.controller.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.repository.animal.CatRepository;
import com.example.team5animalsheltertelegrambot.service.animal.CatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.team5animalsheltertelegrambot.constant.AnimalConstants.*;
import static com.example.team5animalsheltertelegrambot.constant.ShelterConstants.DEFAULT_NAME;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatController.class)
class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;

    @MockBean
    private CatRepository catRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Cat cat1;
    private Cat cat2;
    private Cat cat3;
    private Cat cat4;

    private AnimalReport report;

    private CatShelter catShelter;

    private Cat incorrectCat;
    private List<Cat> cats;
    private List<Cat> healthyCats;
    private List<Cat> vaccinatedCats;
    private List<Cat> healthyAndUnvaccinatedCats;

    @BeforeEach
    void setUp() {
        report = new AnimalReport();
        report.setId(1);
        report.setPhoto("photo");
        report.setDiet("diet");
        report.setWellBeing("wellBeing");
        report.setBehavior("behavior");

        catShelter = new CatShelter();
        catShelter.setId(1);
        catShelter.setName("DEFAULT_NAME");

        cat1 = new Cat("Glafira", 3, true, false);
        cat1.setId(1);
        cat1.setAnimalReports(List.of(report));
        cat1.setCatShelter(new CatShelter());
        cat2 = new Cat("Sofia", 1, true, false);
        cat1.setId(2);
        cat3 = new Cat("Boris", 4, false, true);
        cat1.setId(3);
        cat4 = new Cat("Yasha", 1, false, false);
        cat1.setId(4);

        incorrectCat = new Cat("Yasha", 1, null, false);
        cat1.setId(5);

        cats = List.of(cat1, cat2, cat3, cat4);
        healthyCats = List.of(cat1, cat2);
        vaccinatedCats = List.of(cat3);
        healthyAndUnvaccinatedCats = List.of(cat1, cat2);
    }

    @Test
    void saveShouldReturn200() throws Exception {
        when(catRepository.save(any())).thenReturn(cat1);
        when(catService.save(cat1)).thenReturn(true);

        this.mockMvc.perform(
                        post("/cat")
                                .content(objectMapper.writeValueAsString(cat1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(cat1.getId())))
                .andExpect(jsonPath("$.name", Matchers.is(cat1.getName())))
                .andExpect(jsonPath("$.age", Matchers.is(cat1.getAge())))
                .andExpect(jsonPath("$.healthy", Matchers.is(cat1.getHealthy())))
                .andExpect(jsonPath("$.vaccinated", Matchers.is(cat1.getVaccinated())));
    }

    @Test
    void saveShouldReturn400() throws Exception {
        when(catRepository.save(incorrectCat)).thenReturn(incorrectCat);
        when(catService.save(incorrectCat)).thenReturn(false);

        this.mockMvc.perform(
                        post("/cat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(incorrectCat)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdShouldReturn200() throws Exception {
        when(catRepository.findById(cat1.getId())).thenReturn(Optional.of(cat1));
        when(catService.findById(cat1.getId())).thenReturn(Optional.of(cat1));

        this.mockMvc.perform(
                        get("/cat/{id}", cat1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(cat1.getId())))
                .andExpect(jsonPath("$.name", Matchers.is(cat1.getName())))
                .andExpect(jsonPath("$.age", Matchers.is(cat1.getAge())))
                .andExpect(jsonPath("$.healthy", Matchers.is(cat1.getHealthy())))
                .andExpect(jsonPath("$.vaccinated", Matchers.is(cat1.getVaccinated())));
    }

    @Test
    void findByIdShouldReturn404() throws Exception {
        when(catRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(catService.findById(anyInt())).thenReturn(Optional.empty());

        this.mockMvc.perform(
                        get("/cat/{id}", 6))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllByHealthAndVaccinationShouldReturnListOfHealthyCats() throws Exception {
        when(catRepository.findAllByHealth(true)).thenReturn(healthyCats);
        when(catService.findAllByHealth(true)).thenReturn(healthyCats);
        this.mockMvc.perform(
                        get("/cat/filter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(healthyCats.size())));
    }

    @Test
    void findAllByHealthAndVaccinationShouldReturnListOfVaccinatedCats() throws Exception {
        when(catRepository.findAllByVaccination(true)).thenReturn(vaccinatedCats);
        when(catService.findAllByVaccinate(true)).thenReturn(vaccinatedCats);
        this.mockMvc.perform(
                        get("/cat/filter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(vaccinatedCats.size())));
    }

    @Test
    void findAllByHealthAndVaccinationShouldReturnListOfHealthyAndUnvaccinatedCats() throws Exception {
        when(catRepository.findAllByHealthAndVaccination(true, false))
                .thenReturn(healthyAndUnvaccinatedCats);
        when(catService.findAllByHealthAndVaccination(true, false))
                .thenReturn(healthyAndUnvaccinatedCats);
        this.mockMvc.perform(
                        get("/cat/filter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(healthyAndUnvaccinatedCats.size())));
    }

    @Test
    void findAllShouldReturn200() throws Exception {
        when(catRepository.findAll()).thenReturn(cats);
        when(catService.findAll()).thenReturn(cats);

        this.mockMvc.perform(
                        get("/cat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.hasSize(cats.size())));
    }

    @Test
    void findAllShouldReturn404() throws Exception {
        when(catRepository.findAll()).thenReturn(new ArrayList<>());
        when(catService.findAll()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(
                        get("/cat"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateByIdShouldReturn200() throws Exception {
        when(catRepository.updateById(cat1.getId(), cat1.getName(), cat1.getAge(), cat1.getHealthy(), cat1.getVaccinated()))
                .thenReturn(1);
        when(catService.updateById(cat1.getId(), cat1.getName(), cat1.getAge(), cat1.getHealthy(), cat1.getVaccinated()))
                .thenReturn(1);
        this.mockMvc.perform(
                        put("/cat/{id}", cat1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(cat1.getId())))
                .andExpect(jsonPath("$.name", Matchers.is(cat1.getName())))
                .andExpect(jsonPath("$.age", Matchers.is(cat1.getAge())))
                .andExpect(jsonPath("$.healthy", Matchers.is(cat1.getHealthy())))
                .andExpect(jsonPath("$.vaccinated", Matchers.is(cat1.getVaccinated())));
    }

    @Test
    void updateByIdShouldReturn400() throws Exception {
        when(catRepository.updateById(incorrectCat.getId(), incorrectCat.getName(), incorrectCat.getAge(), incorrectCat.getHealthy(), incorrectCat.getVaccinated()))
                .thenReturn(0);
        when(catService.updateById(incorrectCat.getId(), incorrectCat.getName(), incorrectCat.getAge(), incorrectCat.getHealthy(), incorrectCat.getVaccinated()))
                .thenReturn(0);
        this.mockMvc.perform(
                        put("/cat/{id}", incorrectCat.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteByIdShouldReturn200() throws Exception {
        when(catRepository.save(cat1)).thenReturn(cat1);
        when(catService.save(cat1)).thenReturn(true);
        mockMvc.perform(
                        delete("/cat/{id}", cat1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIdShouldReturn404() throws Exception {
        when(catRepository.save(incorrectCat)).thenReturn(incorrectCat);
        when(catService.save(incorrectCat)).thenReturn(false);
        mockMvc.perform(
                        delete("/cat/{id}", incorrectCat.getId()))
                .andExpect(status().isNotFound());
    }
}