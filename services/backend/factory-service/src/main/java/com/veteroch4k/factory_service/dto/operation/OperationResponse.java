package com.veteroch4k.factory_service.dto.operation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными об операции")
public record OperationResponse(
        @Schema(description = "Уникальный идентификатор операции")
        Long id,

        @Schema(description = "Название операции")
        String name,

        @Schema(description = "Длительность операции (в днях)")
        Long duration,

        OperationFactoryResponse factory

) {
}
