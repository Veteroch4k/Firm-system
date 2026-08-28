package com.veteroch4k.product.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Стандартный ответ об ошибке")
public record ErrorResponse(

        @Schema(description = "Время возникновения ошибки", example = "2026-08-28T15:30:00")
        LocalDateTime time,

        @Schema(description = "HTTP статус", example = "400")
        int status,

        @Schema(description = "Краткое название ошибки", example = "Bad Request")
        String error,

        @Schema(description = "Подробное сообщение для пользователя", example = "Неверный формат почты")
        String message
) {
}
