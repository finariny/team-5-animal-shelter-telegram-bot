package com.example.team5animalsheltertelegrambot.controller;


import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import com.example.team5animalsheltertelegrambot.repository.DogShelterRepository;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * API контроллер для редактирования базовой информации о приюте собак
 */
@RestController
@RequestMapping("/dogShelter")
@Tag(name="Приют собак", description = "Редактирование информация о приюте для собак")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешный запрос"),
        @ApiResponse(responseCode = "400", description = "Невалидные параметры запроса"),
        @ApiResponse(responseCode = "404", description = "Результат запроса не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка программы")
})
public class DogShelterController {
    private final ShelterService shelterService;
    private DogShelterRepository dogShelterRepository;
    DogShelter dogShelter; // после внесение в базу Эту сроку поменять на:   DogShelter dogShelter = dogShelterRepository.getReferenceById(0);
    public DogShelterController(ShelterService shelterService, DogShelterRepository dogShelterRepository) {
        this.shelterService = shelterService;
        this.dogShelterRepository = dogShelterRepository;
    }



    @PutMapping("/name")
    @Operation(
            summary = "Контроллер по редактированию названия приюта"
    )
    public ResponseEntity<String> updateName(@RequestParam String name){
        return ResponseEntity.ok(shelterService.updateName(dogShelter, name));
    }

    @PutMapping("/address")
    @Operation(
            summary = "Контроллер по редактированию адреса приюта"
    )
    public ResponseEntity<String> updateAddress(@RequestParam String address){
        return ResponseEntity.ok(shelterService.updateAddress(dogShelter, address));
    }

    @PutMapping("/contact")
    @Operation(
            summary = "Контроллер по редактированию контактных данных приюта"
    )
    public ResponseEntity<String> updateContact(@RequestParam String contact){
        return ResponseEntity.ok(shelterService.updateContact(dogShelter, contact));
    }


    @PostMapping(value = "/importDogSchema", consumes = (MediaType.IMAGE_PNG_VALUE) )
    @Operation(
            summary = "загрузка и замена файла .png cо схемой проезда к приюту собак"
    )
    public ResponseEntity<Void> uploadDogSchemaFile(@RequestParam MultipartFile file) {
        try {
            shelterService.importSchemaDataFile(dogShelter,file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PostMapping(value = "/importDogAdvice", consumes = (MediaType.APPLICATION_PDF_VALUE) )
    @Operation(
            summary = "загрузка и замена файла PDF рекомендаций для будущих хозяев животных"
    )
    public ResponseEntity<Void> uploadDogAdviceFile(@RequestParam MultipartFile file) {
        try {
            shelterService.importAdviceDataFile(dogShelter,file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
