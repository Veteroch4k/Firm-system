package com.veteroch4k.toolwarehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Запрос на получение инфы об инструменте")
public record ToolRequest (

        @Schema(description = "Идентификатор Типа Инструмента", example = "10")
        @PositiveOrZero @NotNull
        Long toolTypeId
) {

}
