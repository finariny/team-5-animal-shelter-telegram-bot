package com.example.team5animalsheltertelegrambot.controller;

import com.example.team5animalsheltertelegrambot.entity.Shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.entity.Shelter.DogShelter;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catShelter")
@Tag(name="Приют кошек", description = "Редактирование информация о приюте для кошек")
public class CatShelterController {
    private ShelterService shelterService;
    private CatShelter catShelter;

    public CatShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PutMapping("/name")
    public String updateName(@RequestParam String name){
        return shelterService.updateName(catShelter, name);
    }
    @PutMapping("/address")
    public String updateAddress(@RequestParam String address){
        return shelterService.updateAddress(catShelter, address);
    }
    @PutMapping("/contact")
    public String updateContact(@RequestParam String contact){
        return shelterService.updateContact(catShelter, contact);
    }
    @PutMapping("/directions")
    public String updateDirections(@RequestParam String directions){
        return shelterService.updateDirections(catShelter, directions);
    }
    @PutMapping("/safetyAdvice")
    public String updateSafetyAdvice(@RequestParam String safetyAdvice){
        return shelterService.updateSafetyAdvice(catShelter, safetyAdvice);
    }


}