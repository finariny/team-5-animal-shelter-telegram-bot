package com.example.team5animalsheltertelegrambot.controller;



import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import com.example.team5animalsheltertelegrambot.repository.DogShelterRepository;
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
import javax.annotation.PostConstruct;
import java.util.List;


/**
 * API контроллер для редактирования базовой информации о приюте собак
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dogShelter")
@Tag(name="Приют собак", description = "Редактирование информация о приюте для собак")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешный запрос"),
        @ApiResponse(responseCode = "400", description = "Невалидные параметры запроса"),
        @ApiResponse(responseCode = "404", description = "Результат запроса не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка программы")
})
public class DogShelterController {

    private final DogShelterRepository dogShelterRepository;


    @PostConstruct
    public void findShelter() {
        dogShelterRepository.findById(1).orElse(null); // приют собак под индексом 1
    }

    @Operation(summary = "Добавление приюта собак")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приют собак добавлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DogShelter.class)))),})
    @PostMapping("/")
    public ResponseEntity<DogShelter> create(@RequestBody DogShelter dogShelter) {
        try {
            return ResponseEntity.ok(dogShelterRepository.save(dogShelter));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение всей информации о приюте"
    )
    public ResponseEntity<DogShelter> findById(@PathVariable Integer id) {
        return ResponseEntity.of(dogShelterRepository.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DogShelter>> getAll() {
        return ResponseEntity.ok(dogShelterRepository.findAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @NotNull Integer id) {
        dogShelterRepository.deleteById(id);
    }

}
