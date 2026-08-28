package com.veteroch4k.product.dto.drawing;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными чертежа")
public record DrawingResponse  (

        @Schema(description = "Уникальный Идентификатор чертежа", example = "14")
        Long id,

        @Schema(description = "Идентификатор операции", example = "8")
        Long operationId,

        @Schema(description = "Идентификатор фабрики", example = "10")
        Long factoryId
){
}
