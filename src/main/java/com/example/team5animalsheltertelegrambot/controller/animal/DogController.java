package com.example.team5animalsheltertelegrambot.controller.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Dog;
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
 * Контроллер для работы с собаками
 */
@RestController
@RequestMapping("/dog")
@Tag(
        name = "Собаки",
        description = "CRUD-операции для работы с собаками"
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "500",
                description = "Произошла ошибка, не зависящая от вызывающей стороны"
        )
})
public class DogController {

    private final AnimalService<Dog> animalService;

    public DogController(AnimalService<Dog> animalService) {
        this.animalService = animalService;
    }

    @Operation(
            summary = "Добавление собаки в приют"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Собака добавлена"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            )
    })
    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody Dog dog) {
        boolean isDogSaved = animalService.save(dog);
        if (isDogSaved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(
            summary = "Получение собаки из приюта, соответствующей переданному в параметре ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Собака найдена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Собака не найдена"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Dog>> findById(@PathVariable Integer id) {
        Optional<Dog> dog = animalService.findById(id);
        if (dog.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dog);
    }

    @Operation(
            summary = "Получение списка собак из приюта, соответствующих переданному в параметре значению состояния здоровья (здоровы/не здоровы)" +
                    " и/или значению наличия/отсутствия вакцинации"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Собаки найдены",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Собаки не найдены"
            )
    })
    @GetMapping("/filter")
    public ResponseEntity<List<Dog>> findAllByHealthAndVaccination(@RequestParam(required = false) Boolean isHealthy, @RequestParam(required = false) Boolean isVaccinated) {
        if (isHealthy != null && isVaccinated != null) {
            List<Dog> listOfDogs = animalService.findAllByHealthAndVaccination(isHealthy, isVaccinated);
            if (listOfDogs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listOfDogs);
        }
        if (isHealthy != null) {
            List<Dog> listOfDogs = animalService.findAllByHealth(isHealthy);
            if (listOfDogs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listOfDogs);
        }
        if (isVaccinated != null) {
            List<Dog> listOfDogs = animalService.findAllByVaccinate(isVaccinated);
            if (listOfDogs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listOfDogs);
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(
            summary = "Получение списка всех собак из приюта"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Собаки найдены",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Собаки не найдены"
            )
    })
    @GetMapping
    public ResponseEntity<List<Dog>> findAll() {
        List<Dog> listOfDogs = animalService.findAll();
        if (listOfDogs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfDogs);
    }

    @Operation(
            summary = "Обновление информации о собаке из приюта"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о собаке обновлена"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateById(@PathVariable Integer id,
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
            summary = "Удаление собаки из приюта, соответствующей переданному в параметре ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Собака удалена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Собака не найдена"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        boolean isDogDeleted = animalService.deleteById(id);
        if (isDogDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
