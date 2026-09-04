package com.veteroch4k.toolwarehouse.controllers;

import com.veteroch4k.toolwarehouse.dto.ToolRequest;
import com.veteroch4k.toolwarehouse.dto.ToolResponse;
import com.veteroch4k.toolwarehouse.services.ToolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
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
@RequestMapping("/api/tool")
@Tag(name = "Tool API", description = "API для управления инструментами")
@Validated
public class ToolController {

    private final ToolService toolService;

    @Operation(summary = "Получить всё инструменты", description = "Возвращает пагинированный список")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Инструменты успешно получены"),
            @ApiResponse(responseCode = "400", description = "Переданы некорректные входные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/all")
    public Page<ToolResponse> getTools(
            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Размер страницы (максимум 100)")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        return toolService.findAllTools(PageRequest.of(page, size));
    }

    @Operation(summary = "Получить инфу о конкретном инструменте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно получены"),
            @ApiResponse(responseCode = "400", description = "Передан невалидный ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Инструмента с переданным ID не существует",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ToolResponse getToolById(
            @Parameter(description = "Уникальный идентификатор инструмента")
            @PathVariable @PositiveOrZero Long id) {

        return toolService.findToolById(id);

    }

    @Operation(summary = "Получить инструменты по имени типа", description = "Возвращает пагинированный список")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно получены"),
            @ApiResponse(responseCode = "400", description = "Переданы невалидные параметры",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/by-type")
    public Page<ToolResponse> getToolsByType(
            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Размер страницы (максимум 100)")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size,

            @Parameter(description = "Название Типа Инструмента", example = "Киянка")
            @RequestParam @NotBlank String typeName
    ) {
        return toolService.findToolsByToolTypeName(typeName.trim(), PageRequest.of(page, size));
    }

    @Operation(summary = "Создать новый инструмент")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешно создан"),
            @ApiResponse(responseCode = "400", description = "Переданы невалидные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Передан несуществующий Тип Инструмента (fk constraint)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/create-tool")
    @ResponseStatus(HttpStatus.CREATED)
    public ToolResponse createTool(
            @Parameter(description = "Данные для создания инструмента")
            @RequestBody @Valid ToolRequest toolRequest) {

        return toolService.saveTool(toolRequest);

    }

    @Operation(summary = "Обновить данные об инструменте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Данные успешно изменены"),
            @ApiResponse(responseCode = "400", description = "Переданы невалидные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Передан несуществующий Тип Инструмента (fk constraint) или передан ID несуществующего инструмента",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTool(
            @Parameter(description = "ID обновляемого инструмента")
            @PathVariable @PositiveOrZero Long id,
            @Parameter(description = "Данные для обновления")
            @RequestBody @Valid ToolRequest toolRequest) {
        toolService.updateTool(id, toolRequest);

    }

    @Operation(summary = "Удалить запись об инструменте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Запись успешно удалена"),
            @ApiResponse(responseCode = "400", description = "Передан невалидный ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Передан ID несуществующего инструмента",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTool(
            @Parameter(description = "ID удаляемого инструмента")
            @PathVariable @PositiveOrZero Long id) {
        toolService.deleteTool(id);

    }

}
