package com.veteroch4k.order.dto.orderDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Запрос на создание заказа")
public record OrderRequestDTO(

        @Schema(description = "Идентификатор продукта", example = "105")
        @Positive @NotNull
        Long productId,

        @Schema(description = "Количество заказываемого товара", example = "5")
        @Positive @NotNull
        Long productQuantity
)

{}
