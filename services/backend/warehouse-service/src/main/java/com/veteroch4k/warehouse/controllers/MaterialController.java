package com.veteroch4k.warehouse.controllers;

import com.veteroch4k.warehouse.dto.MaterialRequest;
import com.veteroch4k.warehouse.dto.MaterialResponse;
import com.veteroch4k.warehouse.exceptions.ErrorResponse;
import com.veteroch4k.warehouse.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/material")
@Tag(name = "Material API", description = "API для управления материалами")
@Validated
public class MaterialController {

    private final MaterialService materialService;

    @Operation(summary = "Получить все материалы", description = "Возвращает пагинированный список")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Материалы получены"),
            @ApiResponse(responseCode = "400", description = "Переданы некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/all")
    public Page<MaterialResponse> getMaterials(

            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Размер страницы (максимум 100)")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return materialService.findAllMaterials(PageRequest.of(page, size));
    }

    @Operation(summary = "Получить данные о конкретном материале")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно получены"),
            @ApiResponse(responseCode = "400", description = "Передан невалидный ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Материал по заданному ID не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public MaterialResponse getMaterial(
            @Parameter(description = "ID искомого материала")
            @PathVariable @PositiveOrZero Long id) {

        return materialService.findMaterialById(id);

    }

    @Operation(summary = "Создать новый материал")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Материал успешно создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/create-material")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMaterial(
            @Parameter(description = "Данные для создания материала")
            @RequestBody @Valid MaterialRequest materialRequest)
    {
        materialService.saveMaterial(materialRequest);
    }

    @Operation(summary = "Обновление данных о материала")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Данные обновлены"),
            @ApiResponse(responseCode = "400", description = "Невалидныек входные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Материал по заданному ID не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateMaterial(
            @Parameter(description = "ID обновляемого материала")
            @PathVariable @PositiveOrZero Long id,

            @Parameter(description = "Данные для обновления")
            @RequestBody @Valid MaterialRequest materialRequest) {
        materialService.updateMaterialById(id, materialRequest);

    }

    @Operation(summary = "Удаление записи о материале")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Запись удалена"),
            @ApiResponse(responseCode = "400", description = "Передан невалидный ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Материал по заданному ID не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterial(
            @Parameter(description = "ID удаляемого материала")
            @PathVariable @PositiveOrZero Long id
    ) {
        materialService.deleteMaterialById(id);
    }

}
