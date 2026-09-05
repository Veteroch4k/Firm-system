package com.veteroch4k.factory_service.dto.operation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными об операции фабрики")
public record OperationFactoryResponse(
        @Schema(description = "Уникальный идентификатор фабрики")
        Long id,

        @Schema(description = "Название фабрики")
        String name
) {
}
