package com.veteroch4k.factory_service.controller;

import com.veteroch4k.factory_service.dto.operation.OperationRequest;
import com.veteroch4k.factory_service.dto.operation.OperationResponse;
import com.veteroch4k.factory_service.exceptions.ErrorResponse;
import com.veteroch4k.factory_service.services.OperationService;
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
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/operation")
@Tag(name = "Operation API", description = "API для управления операциями")
@Validated
public class OperationController {

    private final OperationService operationService;


    @Operation(summary = "Получить все операции", description = "Возвращает пагинированный список")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно получены"),
            @ApiResponse(responseCode = "400", description = "Невалидные переданные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/all")
    public Page<OperationResponse> operations(
            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Размер страницы (максимум 100)")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return operationService.findAllOperations(PageRequest.of(page, size));
    }

    @Operation(summary = "Получить инфу о конкретной операции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно получены"),
            @ApiResponse(responseCode = "400", description = "Передан невалидный ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Операции с переданным ID не существует",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public OperationResponse operationById(
            @Parameter(description = "ID искомой операции")
            @PathVariable @PositiveOrZero Long id) {

        return operationService.findOperationById(id);

    }

    @Operation(summary = "Создать новую операцию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Запись успешно создана"),
            @ApiResponse(responseCode = "400", description = "Переданы невалидные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ошибка внешнего ключа",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public OperationResponse createOperation(
            @Parameter(description = "Данные для создания операции")
            @RequestBody @Valid OperationRequest op) {

        return operationService.createOperation(op);
    }

    @Operation(summary = "Обновление данных об операции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Данные успешно обновлены"),
            @ApiResponse(responseCode = "400", description = "Переданы невалидные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ошибка внешнего ключа или ишли передан ID несуществующий операции",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOperation(
            @Parameter(description = "ID обновляемой операции")
            @PathVariable @PositiveOrZero Long id,

            @Parameter(description = "Данные для обновления")
            @RequestBody @Valid OperationRequest operation) {


        operationService.updateOperation(id, operation);


    }

    @Operation(summary = "Удаление записи об операции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Операция успешно удалена"),
            @ApiResponse(responseCode = "400", description = "Передан невалидный ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Передан ID несуществующий операции",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOperation(
            @Parameter(description = "ID удаляемой операции")
            @PathVariable @PositiveOrZero Long id) {
        operationService.deleteOperation(id);
    }


}
