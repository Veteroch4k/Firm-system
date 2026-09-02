package com.veteroch4k.warehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными материала")
public record MaterialResponse (

        @Schema(description = "Уникальный идентификатор материала")
        Long id,

        @Schema(description = "Название материала")
        String name
) {
}
