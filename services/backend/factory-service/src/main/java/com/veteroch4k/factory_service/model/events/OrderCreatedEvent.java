package com.veteroch4k.factory_service.model.events;


public record OrderCreatedEvent(
        Long orderId,
        Long productId,
        Long productQuantity
) {}
