package com.example.team5animalsheltertelegrambot.controller;

import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
/**
 * API контроллер для редактирования базовой информации о питомце кошек
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
    private ShelterService shelterService;
    private CatShelter catShelter;

    public CatShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    /**Контроллер по редактированию названия питомца */
    @PutMapping("/name")
    public ResponseEntity<String> updateName(@RequestParam String name){

        return ResponseEntity.ok(shelterService.updateName(catShelter, name));
    }

    /**Контроллер по редактированию адреса питомца */
    @PutMapping("/address")
    public ResponseEntity<String> updateAddress(@RequestParam String address){
        return ResponseEntity.ok(shelterService.updateAddress(catShelter, address));
    }

    /**Контроллер по редактированию контактных данных питомца */
    @PutMapping("/contact")
    public ResponseEntity<String> updateContact(@RequestParam String contact){
        return ResponseEntity.ok(shelterService.updateContact(catShelter, contact));
    }
    /**
     * Эндпоинт загрузки и замены картинки со схемой проезда к питомцу
     *
     * @param file png со схемой проезда к питомцу
     * @return заменяет сохраненный на жестком (локальном) диске файл со схемой на новый
     */
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

    /**
     * Эндпоинт загрузки и замены файла рекомендаций для будущих хозяев животных
     *
     * @param file PDF с рекомендациями
     * @return заменяет сохраненный на жестком (локальном) диске файл PDF
     */
    @PostMapping(value = "/importCatAdvice", consumes = (MediaType.APPLICATION_PDF_VALUE) )
    public ResponseEntity<Void> uploadCatAdviceFile(@RequestParam MultipartFile file) {
        try {
            shelterService.importSchemaDataFile(catShelter,file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}