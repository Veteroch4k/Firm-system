package com.veteroch4k.factory_service.models.events;


public record OrderCreatedEvent(
    int orderId,
    int productId,
    int productQuantity
) {}
