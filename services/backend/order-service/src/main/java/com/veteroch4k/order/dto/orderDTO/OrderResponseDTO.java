package com.veteroch4k.order.dto.orderDTO;


import com.veteroch4k.order.model.Order;

import java.time.LocalDate;

public record OrderResponseDTO(
        Long orderId,
        Long productId,
        Long productQuantity,
        LocalDate orderDate,
        LocalDate finishDate
)
{
    public OrderResponseDTO(Order order) {
        this(order.getId(),  order.getProductId(), order.getProductQuantity(),
                order.getOrderDate(), order.getFinishDate());
    }
}
