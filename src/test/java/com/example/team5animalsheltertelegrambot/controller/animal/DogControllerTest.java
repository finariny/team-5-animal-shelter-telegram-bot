package com.example.team5animalsheltertelegrambot.controller.animal;

import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.repository.person.CustomerRepository;
import com.example.team5animalsheltertelegrambot.service.animal.DogService;
import com.example.team5animalsheltertelegrambot.timer.ProbationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.team5animalsheltertelegrambot.constant.AnimalConstants.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DogController.class)
class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogService dogService;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        CORRECT_DOG_1.setId(1);
        CORRECT_DOG_2.setId(2);
        CORRECT_DOG_3.setId(3);
        CORRECT_DOG_4.setId(4);

        INCORRECT_DOG.setId(5);
    }

    @Test
    void save_ShouldReturn200() throws Exception {
        when(dogService.save(CORRECT_DOG_1)).thenReturn(true);
        this.mockMvc.perform(
                        post("/dog")
                                .content(objectMapper.writeValueAsString(CORRECT_DOG_1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void save_ShouldReturn400() throws Exception {
        when(dogService.save(INCORRECT_DOG)).thenReturn(false);
        this.mockMvc.perform(
                        post("/dog")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(INCORRECT_DOG)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById_ShouldReturn200() throws Exception {
        when(dogService.findById(CORRECT_DOG_1.getId())).thenReturn(Optional.of(CORRECT_DOG_1));
        this.mockMvc.perform(
                        get("/dog/{id}", CORRECT_DOG_1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(CORRECT_DOG_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(CORRECT_DOG_1.getId())))
                .andExpect(jsonPath("$.name", Matchers.is(CORRECT_DOG_1.getName())))
                .andExpect(jsonPath("$.age", Matchers.is(CORRECT_DOG_1.getAge())))
                .andExpect(jsonPath("$.healthy", Matchers.is(CORRECT_DOG_1.getHealthy())))
                .andExpect(jsonPath("$.vaccinated", Matchers.is(CORRECT_DOG_1.getVaccinated())));
    }

    @Test
    void findById_ShouldReturn404() throws Exception {
        when(dogService.findById(5)).thenReturn(Optional.empty());
        this.mockMvc.perform(
                        get("/dog/{id}", 5))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllByHealthAndVaccination_ShouldReturn_ListOfHealthyDogs() throws Exception {
        when(dogService.findAllByHealth(true)).thenReturn(LIST_OF_HEALTHY_DOGS);
        this.mockMvc.perform(
                        get("/dog/filter/?isHealthy=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(LIST_OF_HEALTHY_DOGS.size())));
    }

    @Test
    void findAllByHealthAndVaccination_ShouldReturnListOfVaccinatedDogs() throws Exception {
        when(dogService.findAllByVaccinate(true)).thenReturn(LIST_OF_VACCINATED_DOGS);
        this.mockMvc.perform(
                        get("/dog/filter?isVaccinated=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(LIST_OF_VACCINATED_DOGS.size())));
    }

    @Test
    void findAllByHealthAndVaccination_ShouldReturnListOfHealthyAndUnvaccinatedDogs() throws Exception {
        when(dogService.findAllByHealthAndVaccination(true, false))
                .thenReturn(LIST_OF_HEALTHY_AND_UNVACCINATED_DOGS);
        this.mockMvc.perform(
                        get("/dog/filter/?isHealthy=true&isVaccinated=false")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(LIST_OF_HEALTHY_AND_UNVACCINATED_DOGS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(LIST_OF_HEALTHY_AND_UNVACCINATED_DOGS.size())));
    }

    @Test
    void findAll_ShouldReturn200() throws Exception {
        when(dogService.findAll()).thenReturn(LIST_OF_CORRECT_DOGS);
        this.mockMvc.perform(
                        get("/dog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(LIST_OF_CORRECT_DOGS.size())))
                .andExpect(jsonPath("$[0].id", Matchers.is(LIST_OF_CORRECT_DOGS.get(0).getId())))
                .andExpect(jsonPath("$[1].id", Matchers.is(LIST_OF_CORRECT_DOGS.get(1).getId())))
                .andExpect(jsonPath("$[2].id", Matchers.is(LIST_OF_CORRECT_DOGS.get(2).getId())))
                .andExpect(jsonPath("$[3].id", Matchers.is(LIST_OF_CORRECT_DOGS.get(3).getId())))
                .andExpect(jsonPath("$[0].name", Matchers.is(LIST_OF_CORRECT_DOGS.get(0).getName())))
                .andExpect(jsonPath("$[1].name", Matchers.is(LIST_OF_CORRECT_DOGS.get(1).getName())))
                .andExpect(jsonPath("$[2].name", Matchers.is(LIST_OF_CORRECT_DOGS.get(2).getName())))
                .andExpect(jsonPath("$[3].name", Matchers.is(LIST_OF_CORRECT_DOGS.get(3).getName())))
                .andExpect(jsonPath("$[0].age", Matchers.is(LIST_OF_CORRECT_DOGS.get(0).getAge())))
                .andExpect(jsonPath("$[1].age", Matchers.is(LIST_OF_CORRECT_DOGS.get(1).getAge())))
                .andExpect(jsonPath("$[2].age", Matchers.is(LIST_OF_CORRECT_DOGS.get(2).getAge())))
                .andExpect(jsonPath("$[3].age", Matchers.is(LIST_OF_CORRECT_DOGS.get(3).getAge())))
                .andExpect(jsonPath("$[0].healthy", Matchers.is(LIST_OF_CORRECT_DOGS.get(0).getHealthy())))
                .andExpect(jsonPath("$[1].healthy", Matchers.is(LIST_OF_CORRECT_DOGS.get(1).getHealthy())))
                .andExpect(jsonPath("$[2].healthy", Matchers.is(LIST_OF_CORRECT_DOGS.get(2).getHealthy())))
                .andExpect(jsonPath("$[3].healthy", Matchers.is(LIST_OF_CORRECT_DOGS.get(3).getHealthy())))
                .andExpect(jsonPath("$[0].vaccinated", Matchers.is(LIST_OF_CORRECT_DOGS.get(0).getVaccinated())))
                .andExpect(jsonPath("$[1].vaccinated", Matchers.is(LIST_OF_CORRECT_DOGS.get(1).getVaccinated())))
                .andExpect(jsonPath("$[2].vaccinated", Matchers.is(LIST_OF_CORRECT_DOGS.get(2).getVaccinated())))
                .andExpect(jsonPath("$[3].vaccinated", Matchers.is(LIST_OF_CORRECT_DOGS.get(3).getVaccinated())));
    }

    @Test
    void findAll_ShouldReturn404() throws Exception {
        when(dogService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(
                        get("/dog"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateById_ShouldReturn200() throws Exception {
        when(dogService.updateById(
                CORRECT_DOG_1.getId(),
                CORRECT_DOG_1.getName(),
                CORRECT_DOG_1.getAge(),
                CORRECT_DOG_1.getHealthy(),
                CORRECT_DOG_1.getVaccinated())).thenReturn(1);
        final String pathAndQueryUri = String.format("/dog/%d?name=%s&age=%d&isHealthy=%b&isVaccinated=%b",
                CORRECT_DOG_1.getId(),
                CORRECT_DOG_1.getName(),
                CORRECT_DOG_1.getAge(),
                CORRECT_DOG_1.getHealthy(),
                CORRECT_DOG_1.getVaccinated());
        this.mockMvc.perform(
                put(pathAndQueryUri)).andExpect(status().isOk());
    }

    @Test
    void updateById_ShouldReturn400() throws Exception {
        when(dogService.updateById(
                INCORRECT_DOG.getId(),
                INCORRECT_DOG.getName(),
                INCORRECT_DOG.getAge(),
                INCORRECT_DOG.getHealthy(),
                INCORRECT_DOG.getVaccinated()))
                .thenReturn(0);
        this.mockMvc.perform(
                        put("/dog/{id}", INCORRECT_DOG.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteById_ShouldReturn200() throws Exception {
        when(dogService.deleteById(CORRECT_DOG_1.getId())).thenReturn(true);
        mockMvc.perform(
                        delete("/dog/{id}", CORRECT_DOG_1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById_ShouldReturn404() throws Exception {
        when(dogService.deleteById(INCORRECT_DOG.getId())).thenReturn(false);
        mockMvc.perform(
                        delete("/dog/{id}", INCORRECT_DOG.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void adopt_ShouldReturn200() throws Exception {
        Customer customer = new Customer("FirstName", "LastName", 1000000001L);
        customer.setId(10001);
        when(dogService.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(CORRECT_DOG_1));
        when(dogService.adopt(CORRECT_DOG_1, customer, ProbationType.DEADLINE_30)).thenReturn(true);
        when(customerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(customer));

        final String pathAndQueryUri = String.format("/dog/adopt?dogId=%d&customerId=%d&probationType=DEADLINE_30",
                CORRECT_DOG_1.getId(),
                customer.getId());
        this.mockMvc.perform(put(pathAndQueryUri)).andExpect(status().isOk());
    }
}
