package com.veteroch4k.product.controllers;

import com.veteroch4k.product.dto.ProductManufacturingInfoResponse;
import com.veteroch4k.product.dto.product.ProductResponse;
import com.veteroch4k.product.exceptions.ErrorResponse;
import com.veteroch4k.product.services.ProductService;
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
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "API для управления товарами")
@Validated
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Получить все товары", description = "Возвращает пагинированный список")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товары успешно получены"),
            @ApiResponse(responseCode = "400", description = "Переданы некорректные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/all")
    public Page<ProductResponse> getProducts(
            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Размер страницы (максимум 100)")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        return productService.findAllProducts(PageRequest.of(page, size));
    }

    @Operation(summary = "Получить инфо о конкректном товаре по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар успешно получен"),
            @ApiResponse(responseCode = "400", description = "Передан некорректный ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Заданного товара не существует",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/{id}")
    public ProductResponse getProduct(
            @Parameter(description = "ID товара")
            @PathVariable @PositiveOrZero Long id) {
        return productService
                .findProductById(id);

    }

    @Operation(summary = "Получить производственную инфу о товаре")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно получены"),
            @ApiResponse(responseCode = "400", description = "Передан некорректный ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Заданного товара не существует")
    })
    @GetMapping("/{id}/manufacturing-info")
    public ProductManufacturingInfoResponse getManufacturingInfo(
            @Parameter(description = "ID товара")
            @PathVariable @PositiveOrZero Long id) {

        return productService.getProductInfo(id);
    }

}
