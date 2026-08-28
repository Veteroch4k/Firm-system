package com.veteroch4k.product.dto.product;

import com.veteroch4k.product.dto.drawing.DrawingResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными товарама")
public record ProductResponse(

        @Schema(description = "Уникальный идентификатор продукта", example = "20")
        Long id,

        @Schema(description = "Описание продукта", example = "Кожа для руля")
        String description,

        @Schema(description = "Чертеж продукта")
        DrawingResponse drawing

) {

}
