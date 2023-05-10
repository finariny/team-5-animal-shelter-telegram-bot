package com.example.team5animalsheltertelegrambot.controller.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.repository.person.CustomerRepository;
import com.example.team5animalsheltertelegrambot.service.animal.CatService;
import com.example.team5animalsheltertelegrambot.timer.ProbationType;
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
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Positive;
import java.util.List;

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

    private final CatService catService;
    private final CustomerRepository customerRepository;

    public CatController(CatService catService, CustomerRepository customerRepository) {
        this.catService = catService;
        this.customerRepository = customerRepository;
    }

    @Operation(
            summary = "Добавление кошки в приют"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Кошка добавлена"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            )
    })
    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody Cat cat) {
        boolean isCatSaved = catService.save(cat);
        if (isCatSaved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
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
    public ResponseEntity<Cat> findById(@PathVariable Integer id) {
        return ResponseEntity.of(catService.findById(id));
    }

    @Operation(
            summary = "Получение списка кошек из приюта, соответствующих переданному в параметре значению состояния здоровья (здоровы/не здоровы)" +
                    " и/или значению наличия/отсутствия вакцинации"
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
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Кошки не найдены"
            )
    })
    @GetMapping("/filter")
    public ResponseEntity<List<Cat>> findAllByHealthAndVaccination(@RequestParam(required = false) Boolean isHealthy,
                                                                   @RequestParam(required = false) Boolean isVaccinated) {
        if (isHealthy != null && isVaccinated != null) {
            List<Cat> listOfCats = catService.findAllByHealthAndVaccination(isHealthy, isVaccinated);
            if (listOfCats.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listOfCats);
        }
        if (isHealthy != null) {
            List<Cat> listOfCats = catService.findAllByHealth(isHealthy);
            if (listOfCats.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listOfCats);
        }
        if (isVaccinated != null) {
            List<Cat> listOfCats = catService.findAllByVaccinate(isVaccinated);
            if (listOfCats.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listOfCats);
        }
        return ResponseEntity.badRequest().build();
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
        List<Cat> listOfCats = catService.findAll();
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
                    description = "Информация о кошке обновлена"
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
        Integer number = catService.updateById(id, name, age, isHealthy, isVaccinated);
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
                    description = "Кошка удалена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Кошка не найдена"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id) {
        boolean isCatDeleted = catService.deleteById(id);
        if (isCatDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Регистрация усыновления кошки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Усыновление зарегистрировано"),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат")})
    @PutMapping("/adopt")
    public ResponseEntity<Boolean> adopt(
            @RequestParam @Positive Integer catId,
            @RequestParam @Positive Integer customerId,
            @RequestParam ProbationType probationType) {
        try {
            Cat cat = catService.findById(catId).orElseThrow();
            Customer customer = customerRepository.findById(customerId).orElseThrow();
            return ResponseEntity.ok(catService.adopt(cat, customer, probationType));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка регистрации:" + e.getMessage());
        }
    }
}
