package com.veteroch4k.factory_service.models;


public record OrderCreatedEvent(
    Long orderId,
    Long productId,
    Integer productQuantity
) {}
