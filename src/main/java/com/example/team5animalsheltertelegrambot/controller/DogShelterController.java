package com.example.team5animalsheltertelegrambot.controller;


import com.example.team5animalsheltertelegrambot.entity.Shelter.DogShelter;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dogShelter")
@Tag(name="Приют собак", description = "Редактирование информация о приюте для собак")
public class DogShelterController {
    private ShelterService shelterService;
    private DogShelter dogShelter;

    public DogShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PutMapping("/name")
    public String updateName(@RequestParam String name){
        return shelterService.updateName(dogShelter, name);
    }
    @PutMapping("/address")
    public String updateAddress(@RequestParam String address){
        return shelterService.updateAddress(dogShelter, address);
    }
    @PutMapping("/contact")
    public String updateContact(@RequestParam String contact){
        return shelterService.updateContact(dogShelter, contact);
    }
    @PutMapping("/directions")
    public String updateDirections(@RequestParam String directions){
        return shelterService.updateDirections(dogShelter, directions);
    }
    @PutMapping("/safetyAdvice")
    public String updateSafetyAdvice(@RequestParam String safetyAdvice){
        return shelterService.updateSafetyAdvice(dogShelter, safetyAdvice);
    }



}
