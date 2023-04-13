package com.example.team5animalsheltertelegrambot.controller;


import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/dogShelter")
@Tag(name="Приют собак", description = "Редактирование информация о приюте для собак")
public class DogShelterController {
    private ShelterService shelterService;
    private DogShelter dogShelter; // Подтянется ли из этого объекта правильные названия файлов в методах по работе с файлами?
    public DogShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }


    /**Контроллер по редактированию названия питомца */
    @PutMapping("/name")
    public String updateName(@RequestParam String name){
        return shelterService.updateName(dogShelter, name);
    }
    /**Контроллер по редактированию адреса питомца */
    @PutMapping("/address")
    public String updateAddress(@RequestParam String address){
        return shelterService.updateAddress(dogShelter, address);
    }

    /**Контроллер по редактированию контактных данных питомца */
    @PutMapping("/contact")
    public String updateContact(@RequestParam String contact){
        return shelterService.updateContact(dogShelter, contact);
    }

    /**
     * Эндпоинт загрузки и замены картинки со схемой проезда к питомцу
     *
     * @param file png со схемой проезда к питомцу
     * @return заменяет сохраненный на жестком (локальном) диске файл со схемой на новый
     */
    @PostMapping(value = "/importDogSchema", consumes = (MediaType.IMAGE_PNG_VALUE) )
    public ResponseEntity<Void> uploadDogSchemaFile(@RequestParam MultipartFile file) {
        try {
            shelterService.importSchemaDataFile(dogShelter,file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Эндпоинт загрузки и замены файла рекомендаций для будущих хозяев животных
     *
     * @param file PDF с рекомендациями
     * @return заменяет сохраненный на жестком (локальном) диске файл PDF
     */
    @PostMapping(value = "/importDogAdvice", consumes = (MediaType.APPLICATION_PDF_VALUE) )
    public ResponseEntity<Void> uploadDogAdviceFile(@RequestParam MultipartFile file) {
        try {
            shelterService.importSchemaDataFile(dogShelter,file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}