package com.veteroch4k.factory_service.dto.factory;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Ответ с данными о фабрике")
public record FactoryResponse(
        @Schema(description = "Уникальный идентификатор фабрики")
        Long id,

        @Schema(description = "Название фабрики")
        String name,

        List<FactoryOperationsResponse> operations
) {
}
