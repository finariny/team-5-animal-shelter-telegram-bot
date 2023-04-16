package com.example.team5animalsheltertelegrambot.controller;

import com.example.team5animalsheltertelegrambot.entity.AnimalReport;
import com.example.team5animalsheltertelegrambot.service.report.AnimalReportService;
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
import java.util.Optional;

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
    private final AnimalReportService animalReportService;

    public AnimalReportController(AnimalReportService animalReportService) {
        this.animalReportService = animalReportService;
    }


    @Operation(summary = "Поиск отчетов по ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет по идентификатору получен!",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalReport.class))
                    )
            }
    )
    @GetMapping("/{id}/report/get")
    public Optional<AnimalReport> downloadReport(@Parameter(description = "report id") @PathVariable Integer id) {
        return this.animalReportService.findById(id);
    }

    @Operation(summary = "Удаление отчета по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет удален!",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalReport.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void remove(@Parameter(description = "report id") @PathVariable Integer id) {
        this.animalReportService.remove(id);
    }

    @Operation(summary = "Получение коллекции отчетов.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет получен.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalReport.class)
                            )
                    )
            }
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<AnimalReport>> getAll() {
        return ResponseEntity.ok(this.animalReportService.getAll());
    }


}
