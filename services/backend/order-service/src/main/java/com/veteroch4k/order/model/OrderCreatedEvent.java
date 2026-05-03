package com.veteroch4k.order.model;

public record OrderCreatedEvent(
    Integer orderId,
    Integer productId,
    Integer productQuantity
) {}
