package com.veteroch4k.factory_service.dto.operation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Запрос с данными для создания/обновления операции")
public record OperationRequest(
        @Schema(description = "Название операции")
        @NotBlank @Size(min = 1, max = 255)
        String name,

        @Schema(description = "Длительность операции (в днях)")
        @Positive @NotNull
        Long duration,

        @Schema(description = "Идентификатор фабрики")
        @PositiveOrZero @NotNull
        Long factoryId
) {
}
