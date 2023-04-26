package com.example.team5animalsheltertelegrambot.controller;


import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * API контроллер для редактирования базовой информации о приюте кошек
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/catShelter")
@Tag(name = "Приют кошек", description = "Редактирование информация о приюте для кошек")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешный запрос"),
        @ApiResponse(responseCode = "400", description = "Невалидные параметры запроса"),
        @ApiResponse(responseCode = "404", description = "Результат запроса не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка программы")
})

public class CatShelterController {

    private final CatShelterRepository catShelterRepository;
    CatShelter catShelter;

    @PostConstruct
    public void findShelter() {
        catShelter = catShelterRepository.findById(2).orElse(null);// приют кошек под индексом 2
    }

    @Operation(summary = "Добавление приюта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приют добавлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CatShelter.class)))),})
    @PostMapping("/")
    public ResponseEntity<CatShelter> create(@RequestBody CatShelter catShelter) {
        try {
            return ResponseEntity.ok(catShelterRepository.save(catShelter));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CatShelter>> getAll() {
        return ResponseEntity.ok(catShelterRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение всей информации о приюте"
    )
    public ResponseEntity<CatShelter> findById(@PathVariable Integer id) {
        return ResponseEntity.of(catShelterRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @NotNull Integer id) {
        catShelterRepository.deleteById(id);
    }

}