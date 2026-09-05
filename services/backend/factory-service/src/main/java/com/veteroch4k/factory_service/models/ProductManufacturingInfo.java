package com.veteroch4k.factory_service.models;

public record ProductManufacturingInfo(
        Long productId,
    String description,
        Long drawingId,
        Long factoryId,
        Long operationId
) {}