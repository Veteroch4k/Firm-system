package com.veteroch4k.order.controller;

import com.veteroch4k.order.dto.orderDTO.OrderRequestDTO;
import com.veteroch4k.order.dto.orderDTO.OrderResponseDTO;
import com.veteroch4k.order.exceptions.ErrorResponse;
import com.veteroch4k.order.service.OrderService;

import java.time.LocalDate;

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
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "API для управления заказами")
@Validated
public class OrderController {

    private final OrderService service;

    @Operation(summary = "Получить все заказы", description = "Возвращает пагинированный список")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказы получены"),
            @ApiResponse(responseCode = "400", description = "Переданы некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/all")
    public Page<OrderResponseDTO> orders(
            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Размер страницы (максимум 100)")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return service.findPageOrders(PageRequest.of(page, size));
    }


    @Operation(summary = "Создать новый заказ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заказ успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(
            @Parameter(description = "Данные для создания заказа")
            @Valid @RequestBody OrderRequestDTO orderReqest) {

        log.info("Получен запрос на создание заказа. Product ID: {}, Quantity: {}",
                orderReqest.productId(), orderReqest.productQuantity());
        service.createOrder(orderReqest);
    }


    @Operation(summary = "Получить заказы по дате", description = "Возвращает пагинированный список заказов за указанную дату")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказы получены"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/by-date")
    public Page<OrderResponseDTO> getOrdersByDate(
            @Parameter(description = "Дата заказа в формате YYYY-MM-DD")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,

            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Размер страницы (максимум 100)")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return service.findOrdersByOrderingDate(date, PageRequest.of(page, size));
    }


    @Operation(summary = "Получить заказы за период", description = "Возвращает пагинированный список заказов в указанном диапазоне дат")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказы получены"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/between-dates")
    public Page<OrderResponseDTO> getOrdersByDateRange(
            @Parameter(description = "Начальная дата (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,

            @Parameter(description = "Конечная дата (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,

            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Размер страницы (максимум 100)")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {

        log.debug("Поиск заказов между датами {} и {}. Страница: {}, Размер: {}", start, end, page, size);
        return service.findByOrdersByDateBetween(start, end, PageRequest.of(page, size));
    }


    @Operation(summary = "Получить информацию о конкретном заказе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Заказ с таким ID не существует",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(
            @Parameter(description = "Внутренний ID заказа")
            @PathVariable @PositiveOrZero Long id) {
        return service.findOrderById(id);
    }
}


