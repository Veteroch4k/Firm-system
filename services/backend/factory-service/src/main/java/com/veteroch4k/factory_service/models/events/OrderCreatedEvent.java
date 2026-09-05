package com.veteroch4k.factory_service.models.events;


public record OrderCreatedEvent(
        Long orderId,
        Long productId,
        Long productQuantity
) {}
