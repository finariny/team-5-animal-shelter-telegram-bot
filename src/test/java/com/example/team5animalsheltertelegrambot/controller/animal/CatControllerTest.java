package com.example.team5animalsheltertelegrambot.controller.animal;

import com.example.team5animalsheltertelegrambot.repository.animal.CatRepository;
import com.example.team5animalsheltertelegrambot.service.animal.CatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.team5animalsheltertelegrambot.constant.AnimalConstants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatController.class)
@ExtendWith(MockitoExtension.class)
class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;

    @MockBean
    private CatRepository catRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        CORRECT_CAT_1.setId(1);
        CORRECT_CAT_2.setId(2);
        CORRECT_CAT_3.setId(3);
        CORRECT_CAT_4.setId(4);

        INCORRECT_CAT.setId(5);
    }

    @Test
    void save_ShouldReturn200() throws Exception {
        when(catService.save(CORRECT_CAT_1)).thenReturn(true);
        this.mockMvc.perform(
                        post("/cat")
                                .content(objectMapper.writeValueAsString(CORRECT_CAT_1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void save_ShouldReturn400() throws Exception {
        when(catService.save(INCORRECT_CAT)).thenReturn(false);
        this.mockMvc.perform(
                        post("/cat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(INCORRECT_CAT)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById_ShouldReturn200() throws Exception {
        when(catService.findById(CORRECT_CAT_1.getId())).thenReturn(Optional.of(CORRECT_CAT_1));
        this.mockMvc.perform(
                        get("/cat/{id}", CORRECT_CAT_1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(CORRECT_CAT_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(CORRECT_CAT_1.getId())))
                .andExpect(jsonPath("$.name", Matchers.is(CORRECT_CAT_1.getName())))
                .andExpect(jsonPath("$.age", Matchers.is(CORRECT_CAT_1.getAge())))
                .andExpect(jsonPath("$.healthy", Matchers.is(CORRECT_CAT_1.getHealthy())))
                .andExpect(jsonPath("$.vaccinated", Matchers.is(CORRECT_CAT_1.getVaccinated())));
    }

    @Test
    void findById_ShouldReturn404() throws Exception {
        when(catService.findById(5)).thenReturn(Optional.empty());
        this.mockMvc.perform(
                        get("/cat/{id}", 5))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllByHealthAndVaccination_ShouldReturn_ListOfHealthyCats() throws Exception {
        when(catRepository.findAllByHealth(true))
                .thenReturn(LIST_OF_HEALTHY_CATS);
        when(catService.findAllByHealth(true))
                .thenReturn(LIST_OF_HEALTHY_CATS);

        this.mockMvc.perform(
                        get("/cat/filter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(LIST_OF_HEALTHY_CATS.size())));
    }

    @Test
    void findAllByHealthAndVaccination_ShouldReturn_ListOfVaccinatedCats() throws Exception {
        when(catRepository.findAllByVaccination(true)).thenReturn(LIST_OF_VACCINATED_CATS);
        when(catService.findAllByVaccinate(true)).thenReturn(LIST_OF_VACCINATED_CATS);
        this.mockMvc.perform(
                        get("/cat/filter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(LIST_OF_VACCINATED_CATS.size())));
    }

    @Test
    void findAllByHealthAndVaccination_ShouldReturn_ListOfHealthyAndUnvaccinatedCats() throws Exception {
        when(catRepository.findAllByHealthAndVaccination(true, false))
                .thenReturn(LIST_OF_HEALTHY_AND_UNVACCINATED_CATS);
        when(catService.findAllByHealthAndVaccination(true, false))
                .thenReturn(LIST_OF_HEALTHY_AND_UNVACCINATED_CATS);
        this.mockMvc.perform(
                        get("/cat/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LIST_OF_HEALTHY_AND_UNVACCINATED_CATS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(LIST_OF_HEALTHY_AND_UNVACCINATED_CATS.size())));
    }

    @Test
    void findAll_ShouldReturn200() throws Exception {
        when(catService.findAll()).thenReturn(LIST_OF_CORRECT_CATS);
        this.mockMvc.perform(
                        get("/cat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(LIST_OF_CORRECT_CATS.size())))
                .andExpect(jsonPath("$[0].id", Matchers.is(LIST_OF_CORRECT_CATS.get(0).getId())))
                .andExpect(jsonPath("$[1].id", Matchers.is(LIST_OF_CORRECT_CATS.get(1).getId())))
                .andExpect(jsonPath("$[2].id", Matchers.is(LIST_OF_CORRECT_CATS.get(2).getId())))
                .andExpect(jsonPath("$[3].id", Matchers.is(LIST_OF_CORRECT_CATS.get(3).getId())))
                .andExpect(jsonPath("$[0].name", Matchers.is(LIST_OF_CORRECT_CATS.get(0).getName())))
                .andExpect(jsonPath("$[1].name", Matchers.is(LIST_OF_CORRECT_CATS.get(1).getName())))
                .andExpect(jsonPath("$[2].name", Matchers.is(LIST_OF_CORRECT_CATS.get(2).getName())))
                .andExpect(jsonPath("$[3].name", Matchers.is(LIST_OF_CORRECT_CATS.get(3).getName())))
                .andExpect(jsonPath("$[0].age", Matchers.is(LIST_OF_CORRECT_CATS.get(0).getAge())))
                .andExpect(jsonPath("$[1].age", Matchers.is(LIST_OF_CORRECT_CATS.get(1).getAge())))
                .andExpect(jsonPath("$[2].age", Matchers.is(LIST_OF_CORRECT_CATS.get(2).getAge())))
                .andExpect(jsonPath("$[3].age", Matchers.is(LIST_OF_CORRECT_CATS.get(3).getAge())))
                .andExpect(jsonPath("$[0].healthy", Matchers.is(LIST_OF_CORRECT_CATS.get(0).getHealthy())))
                .andExpect(jsonPath("$[1].healthy", Matchers.is(LIST_OF_CORRECT_CATS.get(1).getHealthy())))
                .andExpect(jsonPath("$[2].healthy", Matchers.is(LIST_OF_CORRECT_CATS.get(2).getHealthy())))
                .andExpect(jsonPath("$[3].healthy", Matchers.is(LIST_OF_CORRECT_CATS.get(3).getHealthy())))
                .andExpect(jsonPath("$[0].vaccinated", Matchers.is(LIST_OF_CORRECT_CATS.get(0).getVaccinated())))
                .andExpect(jsonPath("$[1].vaccinated", Matchers.is(LIST_OF_CORRECT_CATS.get(1).getVaccinated())))
                .andExpect(jsonPath("$[2].vaccinated", Matchers.is(LIST_OF_CORRECT_CATS.get(2).getVaccinated())))
                .andExpect(jsonPath("$[3].vaccinated", Matchers.is(LIST_OF_CORRECT_CATS.get(3).getVaccinated())));
    }

    @Test
    void findAll_ShouldReturn404() throws Exception {
        when(catService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(
                        get("/cat"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateById_ShouldReturn200() throws Exception {
        when(catRepository.save(CORRECT_CAT_1)).thenReturn(CORRECT_CAT_1);
        when(catService.save(CORRECT_CAT_1)).thenReturn(true);
        when(catRepository.findById(CORRECT_CAT_1.getId())).thenReturn(Optional.of(CORRECT_CAT_1));
        when(catService.findById(CORRECT_CAT_1.getId())).thenReturn(Optional.of(CORRECT_CAT_1));
        when(catRepository.updateById(CORRECT_CAT_1.getId(), CORRECT_CAT_1.getName(), CORRECT_CAT_1.getAge(), CORRECT_CAT_1.getHealthy(), CORRECT_CAT_1.getVaccinated())).thenReturn(1);
        when(catService.updateById(CORRECT_CAT_1.getId(), CORRECT_CAT_1.getName(), CORRECT_CAT_1.getAge(), CORRECT_CAT_1.getHealthy(), CORRECT_CAT_1.getVaccinated()))
                .thenReturn(1);
        this.mockMvc.perform(
                        put("/cat/{id}", CORRECT_CAT_1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void updateById_ShouldReturn400() throws Exception {
        when(catRepository.findById(INCORRECT_CAT.getId())).thenReturn(Optional.empty());
        when(catService.updateById(INCORRECT_CAT.getId(), INCORRECT_CAT.getName(), INCORRECT_CAT.getAge(), INCORRECT_CAT.getHealthy(), INCORRECT_CAT.getVaccinated()))
                .thenReturn(0);
        this.mockMvc.perform(
                        put("/cat/{id}", INCORRECT_CAT.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteById_ShouldReturn200() throws Exception {
        when(catService.deleteById(CORRECT_CAT_1.getId())).thenReturn(true);
        mockMvc.perform(
                        delete("/cat/{id}", CORRECT_CAT_1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById_ShouldReturn404() throws Exception {
        when(catService.deleteById(INCORRECT_CAT.getId())).thenReturn(false);
        mockMvc.perform(
                        delete("/cat/{id}", INCORRECT_CAT.getId()))
                .andExpect(status().isNotFound());
    }
}