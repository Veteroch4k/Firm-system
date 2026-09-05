package com.veteroch4k.factory_service.dto.factory;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными об операции фабрики")
public record FactoryOperationsResponse(

        @Schema(description = "Уникальный идентификатор операации")
        Long id,

        @Schema(description = "Название операции")
        String name,

        @Schema(description = "Длительность (в днях)")
        Long duration
) {
}
