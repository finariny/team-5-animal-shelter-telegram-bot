package com.example.team5animalsheltertelegrambot.controller;

import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.service.report.impl.AnimalReportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @author Arutyunyan Aikanush
 */
@RestController
@RequestMapping("/reports")
@Tag(
        name = "Отчеты",
        description = "Операции для получения и удаления отчетов"
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "500",
                description = "Произошла ошибка, не зависящая от вызывающей стороны"
        )
})
public class AnimalReportController {
    private final AnimalReportServiceImpl animalReportService;

    public AnimalReportController(AnimalReportServiceImpl animalReportService) {
        this.animalReportService = animalReportService;
    }


    @Operation(summary = "Поиск отчетов по ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Отчет по идентификатору получен!",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AnimalReport.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос!"
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<AnimalReport> downloadReport(@Parameter(description = "report id") @PathVariable Integer id) {
        return ResponseEntity.ok(this.animalReportService.findById(id));
    }

    @Operation(summary = "Удаление отчета по идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Отчет удален!",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AnimalReport.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос!"
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@Parameter(description = "report id") @PathVariable Integer id) {
        if (id != null) {
            animalReportService.remove(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Получение коллекции отчетов.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Отчет получен.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AnimalReport.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос!"
            )
    }
    )
    @GetMapping("/")
    public ResponseEntity<List<AnimalReport>> getAll() {
        return ResponseEntity.ok(this.animalReportService.getAll());
    }


}
