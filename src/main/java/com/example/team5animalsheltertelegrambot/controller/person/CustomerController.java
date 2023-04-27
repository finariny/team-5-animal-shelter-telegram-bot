package com.example.team5animalsheltertelegrambot.controller.person;

import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.repository.person.CustomerRepository;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Контроллер для работы с пользователями (посетители приютов)
 */

@Validated
@RestController
@RequestMapping("/customer")
@Tag(name = "Посетители", description = "CRUD-операции для работы с посетителями")
@ApiResponses(value = {@ApiResponse(responseCode = "500",
        description = "Произошла ошибка, не зависящая от вызывающей стороны")})
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Operation(summary = "Получение списка всех посетителей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Посетители найдены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Customer.class)))),
            @ApiResponse(responseCode = "404", description = "Посетители не найдены")})
    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Получение посетителя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Посетитель найден",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Посетитель не найден")})
    @GetMapping("/id/{id}")
    public ResponseEntity<Customer> findById(@PathVariable @Positive Integer id) {
        return ResponseEntity.of(customerRepository.findById(id));
    }

    @Operation(summary = "Получение посетителя по Telegram chatId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Посетитель найден",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Посетитель не найден")})
    @GetMapping("/chatId/{chatId}")
    public ResponseEntity<Customer> findByChatId(@PathVariable @Positive Long chatId) {
        return ResponseEntity.of(customerRepository.findByChatId(chatId));
    }

    @Operation(summary = "Добавление посетителя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Посетитель добавлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Customer.class)))),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат")})
    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody @Valid Customer customer) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(customerRepository.save(customer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Обновление информации о посетителе по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о посетителе обновлена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Customer.class)))),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат")})
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateById(
            @PathVariable Integer id,
            @RequestParam(name = "chatId", required = false) @Positive Long chatId,
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName) {
        try {
            Customer customer = customerRepository.findById(id).orElseThrow();
            customer.setChatId(chatId);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            return ResponseEntity.ok(customerRepository.save(customer));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка обновления:" + e.getMessage());
        }
    }

    @Operation(summary = "Удаление посетителя из приюта, соответствующей переданному в параметре ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Посетитель удален"),
            @ApiResponse(responseCode = "404", description = "Посетитель не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable @Positive Integer id) {
        try {
            customerRepository.deleteById(id);
            return ResponseEntity.ok().body("Запись удалена");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка удаления:" + e.getMessage());
        }
    }
}
