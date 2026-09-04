package com.veteroch4k.toolwarehouse.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Ответ с описанием ошибки")
public record ErrorResponse(
        @Schema(description = "Время, когда произошла ошибка")
        LocalDateTime time,
        @Schema(description = "Код статуса ошибки", example = "404")
        int status,
        @Schema(description = "Причина ошибки", example = "Not Found")
        String error,

        @Schema(description = "Описание ошибки", example = "Инструмент с ID: 10 не найден.")
        String message
) {
}
