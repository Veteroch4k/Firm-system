package com.veteroch4k.toolwarehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными о Типе Инструмента")
public record ToolTypeResponse(

        @Schema(description = "Уникальный идентификатор Типа Инструмента")
        Long id,

        @Schema(description = "Название Типа Инструмента", example = "Киянка")
        String name,

        @Schema(description = "Описание Типа Инструмента", example = "Дабы бить плохих работников...")
        String description
) {
}
