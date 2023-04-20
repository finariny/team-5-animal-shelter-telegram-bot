package com.example.team5animalsheltertelegrambot.controller;


import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
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
 * API контроллер для редактирования базовой информации о приюте кошек
 */
@RestController
@RequestMapping("/catShelter")
@Tag(name="Приют кошек", description = "Редактирование информация о приюте для кошек")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешный запрос"),
        @ApiResponse(responseCode = "400", description = "Невалидные параметры запроса"),
        @ApiResponse(responseCode = "404", description = "Результат запроса не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка программы")
})
public class CatShelterController {
    private final ShelterService shelterService;
    private CatShelterRepository catShelterRepository;
    CatShelter catShelter; // после внесение в базу Эту сроку поменять на:   CatShelter catShelter = catShelterRepository.getReferenceById(0);

    public CatShelterController(ShelterService shelterService, CatShelterRepository catShelterRepository) {
        this.shelterService = shelterService;
        this.catShelterRepository = catShelterRepository;
    }


    @PostMapping("/name")
    @Operation(
            summary = "Контроллер по назначению названия приюта"
    )
    public void setName(@RequestParam String name){
        catShelter.setName(name);
    }

    @GetMapping("/name")
    @Operation(
            summary = "Контроллер по получению названия приюта"
    )
    public ResponseEntity<String> getName(){
        return ResponseEntity.ok(catShelter.getName());
    }

    @PutMapping("/name")
    @Operation(
            summary = "Контроллер по редактированию названия приюта"
    )
    public ResponseEntity<String> updateName(@RequestParam String name){

        return ResponseEntity.ok(shelterService.updateName(catShelter, name));
    }
    @PostMapping("/address")
    @Operation(
            summary = "Контроллер по назначению адреса приюта"
    )
    public void setAddress(@RequestParam String str){
        catShelter.setAddress(str);
    }

    @GetMapping("/address")
    @Operation(
            summary = "Контроллер по получению адреса приюта"
    )
    public ResponseEntity<String> getAddress(){
        return ResponseEntity.ok(catShelter.getAddress());
    }


    @PutMapping("/address")
    @Operation(
            summary = "Контроллер по редактированию адреса приюта "
    )
    public ResponseEntity<String> updateAddress(@RequestParam String address){
        return ResponseEntity.ok(shelterService.updateAddress(catShelter, address));
    }

    @PostMapping("/contact")
    @Operation(
            summary = "Контроллер по назначению телефона приюта"
    )
    public void setContact(@RequestParam String str){
        catShelter.setContacts(str);
    }

    @GetMapping("/contact")
    @Operation(
            summary = "Контроллер по получению телефона приюта"
    )
    public ResponseEntity<String> getContact(){
        return ResponseEntity.ok(catShelter.getContacts());
    }
    @PutMapping("/contact")
    @Operation(
            summary = "Контроллер по редактированию контактных данных приюта "
    )
    public ResponseEntity<String> updateContact(@RequestParam String contact){
        return ResponseEntity.ok(shelterService.updateContact(catShelter, contact));
    }

    @PostMapping("/description")
    @Operation(
            summary = "Контроллер по назначению описания приюта"
    )
    public void setDescription(@RequestParam String str){
        catShelter.setContacts(str);
    }

    @GetMapping("/description")
    @Operation(
            summary = "Контроллер по получению описания приюта"
    )
    public ResponseEntity<String> getDescription(){
        return ResponseEntity.ok(catShelter.getContacts());
    }
    @PutMapping("/description")
    @Operation(
            summary = "Контроллер по редактированию контактных данных приюта "
    )
    public ResponseEntity<String> updateDescription(@RequestParam String description){
        return ResponseEntity.ok(shelterService.updateContact(catShelter, description));
    }

    @Operation(
            summary = "загрузка и замена файла .png cо схемой проезда к приюту кошек"
    )
    @PostMapping(value = "/importCatSchema", consumes = (MediaType.IMAGE_PNG_VALUE) )
    public ResponseEntity<Void> uploadCatSchemaFile(@RequestParam MultipartFile file) {
        try {
            shelterService.importSchemaDataFile(catShelter,file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/importCatAdvice", consumes = (MediaType.APPLICATION_PDF_VALUE) )
    @Operation(
            summary = "загрузка и замена файла PDF рекомендаций для будущих хозяев животных"
    )
    public ResponseEntity<Void> uploadCatAdviceFile(@RequestParam MultipartFile file) {
        try {
            shelterService.importAdviceDataFile(catShelter,file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}