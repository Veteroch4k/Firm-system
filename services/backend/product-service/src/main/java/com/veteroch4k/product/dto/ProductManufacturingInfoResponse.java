package com.veteroch4k.product.dto;

public record ProductManufacturingInfoResponse(
    Long productId,
    String description,
    Long drawingId,
    Long factoryId,
    Long operationId
) {}