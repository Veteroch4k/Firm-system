package com.veteroch4k.order.model;

public record OrderCreatedEvent(
    Long orderId,
    Long productId,
    Integer productQuantity
) {}
