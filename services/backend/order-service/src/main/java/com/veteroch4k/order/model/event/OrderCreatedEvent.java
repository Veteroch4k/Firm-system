package com.veteroch4k.order.model.event;

public record OrderCreatedEvent(
    Long orderId,
    Long productId,
    Long productQuantity
) {}
