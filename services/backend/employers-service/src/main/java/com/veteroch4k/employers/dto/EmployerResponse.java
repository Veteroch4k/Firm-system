package com.veteroch4k.employers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными сотрудника")
public record EmployerResponse(

        @Schema(description = "Уникальный идентификатор сотрудника", example = "123")
        Long id,
        @Schema(description = "Имя сотрудника", example = "Виктор")
        String name
) {
}
