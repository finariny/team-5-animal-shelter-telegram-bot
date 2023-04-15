package com.example.team5animalsheltertelegrambot.controller.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для работы с кошками
 */
@RestController
@RequestMapping("/cat")
@Tag(
        name = "Кошки",
        description = "CRUD-операции для работы с кошками"
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "500",
                description = "Произошла ошибка, не зависящая от вызывающей стороны"
        )
})
public class CatController {

    private final AnimalService<Cat> animalService;

    public CatController(AnimalService<Cat> animalService) {
        this.animalService = animalService;
    }

    @Operation(
            summary = "Добавление кошки в приют"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Кошка добавлена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            )
    })
    @PostMapping
    public ResponseEntity<Cat> save(@RequestBody Cat cat) {
        Cat newCat = animalService.save(cat);
        if (newCat == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newCat);
    }

    @Operation(
            summary = "Получение кошки из приюта, соответствующей переданному в параметре ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Кошка найдена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Кошка не найдена"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cat>> findById(@PathVariable Integer id) {
        Optional<Cat> cat = animalService.findById(id);
        if (cat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cat);
    }

    @Operation(
            summary = "Получение списка кошек из приюта, соответствующих переданному в параметре значению состояния здоровья (здоровы/не здоровы)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Кошки найдены",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Кошки не найдены"
            )
    })
    @GetMapping("/byHealth/{isHealthy}")
    public ResponseEntity<List<Cat>> findAllByHealth(@PathVariable Boolean isHealthy) {
        List<Cat> listOfCats = animalService.findAllByHealth(isHealthy);
        if (listOfCats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfCats);
    }

    @Operation(
            summary = "Получение списка кошек из приюта, соответствующих переданному в параметре значению наличия/отсутствия вакцинации"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Кошки найдены",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Кошки не найдены"
            )
    })
    @GetMapping("/byVaccinate/{isVaccinated}")
    public ResponseEntity<List<Cat>> findAllByVaccinate(@PathVariable Boolean isVaccinated) {
        List<Cat> listOfCats = animalService.findAllByVaccinate(isVaccinated);
        if (listOfCats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfCats);
    }

    @Operation(
            summary = "Получение списка всех кошек из приюта"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Кошки найдены",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Кошки не найдены"
            )
    })
    @GetMapping
    public ResponseEntity<List<Cat>> findAll() {
        List<Cat> listOfCats = animalService.findAll();
        if (listOfCats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfCats);
    }

    @Operation(
            summary = "Обновление информации о кошке из приюта"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о кошке обновлена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cat> updateById(@PathVariable Integer id,
                                          @RequestParam String name,
                                          @RequestParam Integer age,
                                          @RequestParam Boolean isHealthy,
                                          @RequestParam Boolean isVaccinated) {
        Integer number = animalService.updateById(id, name, age, isHealthy, isVaccinated);
        if (number == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Удаление кошки из приюта, соответствующей переданному в параметре ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Кошка удалена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Кошка не найдена"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        Optional<Cat> cat = animalService.findById(id);
        if (cat.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        animalService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
