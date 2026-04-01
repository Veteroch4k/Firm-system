package com.veteroch4k.product.models;

public record ProductManufacturingInfo(
    Integer productId,
    String description,
    Integer drawingId,
    Integer operationId
) {}