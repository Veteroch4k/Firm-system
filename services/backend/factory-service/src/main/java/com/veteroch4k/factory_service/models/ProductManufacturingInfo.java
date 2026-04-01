package com.veteroch4k.factory_service.models;

public record ProductManufacturingInfo(
    Integer productId,
    String description,
    Integer drawingId,
    Integer operationId
) {}