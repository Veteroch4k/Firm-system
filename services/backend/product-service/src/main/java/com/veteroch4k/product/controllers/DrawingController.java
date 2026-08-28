package com.veteroch4k.product.controllers;

import com.veteroch4k.product.dto.drawing.DrawingResponse;
import com.veteroch4k.product.exceptions.ErrorResponse;
import com.veteroch4k.product.services.DrawingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/drawing")
@Tag(name = "Drawing API", description = "API для управления чертежами")
@Validated
public class DrawingController {

    private final DrawingService drawingService;

    @Operation(summary = "Получить все чертежи", description = "Возвращает пагинированный список")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Чертежи получены"),
            @ApiResponse(responseCode = "400", description = "Переданы некорректные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/all")
    public Page<DrawingResponse> getDrawings(
            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Размер страницы (максимум 100)")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        return drawingService.findAllDrawings(PageRequest.of(page, size));
    }

    @Operation(summary = "Получить информацию о конкретном чертеже")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Чертеж найжен"),
            @ApiResponse(responseCode = "400", description = "Некорректный ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Заказ с таким ID не существует",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/{id}")
    public DrawingResponse getDrawing(
            @Parameter(description = "Внутренний ID чертежа")
            @PathVariable @Positive Long id) {

        return drawingService
                .findDrawingById(id);
    }

}
