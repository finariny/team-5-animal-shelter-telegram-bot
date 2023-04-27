package com.example.team5animalsheltertelegrambot.controller.person;

import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.repository.person.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerRepository customerRepository;

    List<Customer> customers;

    @BeforeEach
    void setUp() {
        Customer customer1 = new Customer("FirstName1", "LastName1", 1000000001L);
        customer1.setId(10001);
        Customer customer2 = new Customer("FirstName2", "LastName2", 1000000002L);
        customer2.setId(10002);
        customers = List.of(customer1, customer2);
    }

    @Test
    void findAll() throws Exception {
        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        this.mockMvc.perform(
                        get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].chatId").value(1000000001L))
                .andExpect(jsonPath("$[1].chatId").value(1000000002L))
                .andExpect(jsonPath("$[0].id").value(10001))
                .andExpect(jsonPath("$[1].id").value(10002))
                .andExpect(jsonPath("$[0].firstName").value("FirstName1"))
                .andExpect(jsonPath("$[1].firstName").value("FirstName2"))
                .andExpect(jsonPath("$[0].lastName").value("LastName1"))
                .andExpect(jsonPath("$[1].lastName").value("LastName2"));
    }

    @Test
    void findById() throws Exception {
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(customers.get(0)));
        this.mockMvc.perform(
                        get("/customer/id/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("LastName1"))
                .andExpect(jsonPath("$.firstName").value("FirstName1"))
                .andExpect(jsonPath("$.chatId").value(1000000001L));
    }

    @Test
    void findByChatId() throws Exception {
        Mockito.when(customerRepository.findByChatId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(customers.get(1)));
        this.mockMvc.perform(
                        get("/customer/chatId/{chatId}", 1000000002L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("LastName2"))
                .andExpect(jsonPath("$.firstName").value("FirstName2"));
    }

    @Test
    void save() throws Exception {
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customers.get(0));
        mockMvc.perform(
                        post("/customer")
                                .content(objectMapper.writeValueAsString(customers.get(0)))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(customers.get(0))));
    }

    @Test
    void updateById() throws Exception {
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customers.get(1));
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(customers.get(0)));
        mockMvc.perform(
                        put("/customer/1")
                                .content(objectMapper.writeValueAsString(customers.get(1)))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("LastName2"))
                .andExpect(jsonPath("$.firstName").value("FirstName2"));
    }

    @Test
    void deleteById() throws Exception {
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customers.get(1));
        mockMvc.perform(
                        delete("/customer/1"))
                .andExpect(status().isOk());
    }
}