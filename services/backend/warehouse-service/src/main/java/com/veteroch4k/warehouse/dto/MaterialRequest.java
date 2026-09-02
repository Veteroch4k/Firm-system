package com.veteroch4k.warehouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Schema(description = "Запрос на создание/изменение материала")
public record MaterialRequest (

        @Schema(description = "Название материала")
        @NotBlank @Length(min = 1, max = 25)
        String name
) {
}
