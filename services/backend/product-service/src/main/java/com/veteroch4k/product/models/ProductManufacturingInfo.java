package com.veteroch4k.product.models;

public record ProductManufacturingInfo(
    Long productId,
    String description,
    Long drawingId,
    Long factoryId,
    Long operationId
) {}