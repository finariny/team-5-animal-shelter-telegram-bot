package com.example.team5animalsheltertelegrambot.controller;

import com.example.team5animalsheltertelegrambot.service.bot.BotCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Positive;

/**
 * Контроллер для работы с телеграм ботом
 */

@Validated
@RestController
@RequestMapping("/bot")
@Tag(name = "Бот", description = "операции для работы с телеграм ботом")
@ApiResponses(value = {@ApiResponse(responseCode = "500",
        description = "Произошла ошибка, не зависящая от вызывающей стороны")})
public class BotController {
    private final BotCommandService botCommandService;

    public BotController(BotCommandService botCommandService) {
        this.botCommandService = botCommandService;
    }

    @Operation(summary = "Отправка текстового сообщения с форматированием в Markdown по chatId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение отправлено"),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат")})
    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody String message, @Positive long chatId) {
        try {
            botCommandService.sendMessage(chatId, message);
            return ResponseEntity.ok().body("Сообщение отправлено");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
