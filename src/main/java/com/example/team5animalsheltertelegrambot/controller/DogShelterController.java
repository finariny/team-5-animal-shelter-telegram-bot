package com.example.team5animalsheltertelegrambot.controller;


import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import com.example.team5animalsheltertelegrambot.repository.DogShelterRepository;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private final DogShelterRepository dogShelterRepository;
    DogShelter dogShelter; // после внесение в базу Эту сроку поменять на:   DogShelter dogShelter = dogShelterRepository.getReferenceById(0);
    public DogShelterController(ShelterService shelterService, DogShelterRepository dogShelterRepository) {
        this.shelterService = shelterService;
        this.dogShelterRepository = dogShelterRepository;
    }

    @Operation(summary = "Добавление приюта собак")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приют собак добавлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Customer.class)))),})
    @PostMapping
    public ResponseEntity<DogShelter> save(@RequestBody DogShelter dogShelter) {
        try {
            return ResponseEntity.ok(dogShelterRepository.save(dogShelter));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/name")
    @Operation(
            summary = "Контроллер по назначению названия приюта"
    )
    public void setName(@RequestParam String name){
        dogShelter.setName(name);
    }

    @GetMapping("/name")
    @Operation(
            summary = "Контроллер по получению названия приюта"
    )
    public ResponseEntity<String> getName(){
        return ResponseEntity.ok(dogShelter.getName());
    }

    @PutMapping("/name")
    @Operation(
            summary = "Контроллер по редактированию названия приюта"
    )
    public ResponseEntity<String> updateName(@RequestParam String name){

        return ResponseEntity.ok(shelterService.updateName(dogShelter, name));
    }
    @PostMapping("/address")
    @Operation(
            summary = "Контроллер по назначению адреса приюта"
    )
    public void setAddress(@RequestParam String str){
        dogShelter.setAddress(str);
    }

    @GetMapping("/address")
    @Operation(
            summary = "Контроллер по получению адреса приюта"
    )
    public ResponseEntity<String> getAddress(){
        return ResponseEntity.ok(dogShelter.getAddress());
    }


    @PutMapping("/address")
    @Operation(
            summary = "Контроллер по редактированию адреса приюта "
    )
    public ResponseEntity<String> updateAddress(@RequestParam String address){
        return ResponseEntity.ok(shelterService.updateAddress(dogShelter, address));
    }

    @PostMapping("/contact")
    @Operation(
            summary = "Контроллер по назначению телефона приюта"
    )
    public void setContact(@RequestParam String str){
        dogShelter.setContacts(str);
    }

    @GetMapping("/contact")
    @Operation(
            summary = "Контроллер по получению телефона приюта"
    )
    public ResponseEntity<String> getContact(){
        return ResponseEntity.ok(dogShelter.getContacts());
    }
    @PutMapping("/contact")
    @Operation(
            summary = "Контроллер по редактированию контактных данных приюта "
    )
    public ResponseEntity<String> updateContact(@RequestParam String contact){
        return ResponseEntity.ok(shelterService.updateContact(dogShelter, contact));
    }

    @PostMapping("/description")
    @Operation(
            summary = "Контроллер по назначению описания приюта"
    )
    public void setDescription(@RequestParam String str){
        dogShelter.setContacts(str);
    }

    @GetMapping("/description")
    @Operation(
            summary = "Контроллер по получению описания приюта"
    )
    public ResponseEntity<String> getDescription(){
        return ResponseEntity.ok(dogShelter.getContacts());
    }
    @PutMapping("/description")
    @Operation(
            summary = "Контроллер по редактированию контактных данных приюта "
    )
    public ResponseEntity<String> updateDescription(@RequestParam String description){
        return ResponseEntity.ok(shelterService.updateContact(dogShelter, description));
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
