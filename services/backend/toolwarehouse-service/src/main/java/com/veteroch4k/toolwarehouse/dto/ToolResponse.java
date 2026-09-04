package com.veteroch4k.toolwarehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с информацией об инструменте")
public record ToolResponse(

        @Schema(description = "Уникальный идентификатор инструмента", example = "11")
        Long id,

        ToolTypeResponse toolType
) {
}
