package com.veteroch4k.order.dto.orderDTO;


import com.veteroch4k.order.model.Order;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Ответ с данными заказа")
public record OrderResponseDTO(

        @Schema(description = "Идентификатор заказа", example = "1")
        Long orderId,

        @Schema(description = "Идентификатор продукта", example = "105")
        Long productId,

        @Schema(description = "Количество заказываемого товара", example = "5")
        Long productQuantity,

        @Schema(description = "Дата создания заказа", example = "2026-08-24")
        LocalDate orderDate,

        @Schema(description = "Ориентировочная дата завершения заказа", example = "2026-09-03")
        LocalDate finishDate
)
{
    public OrderResponseDTO(Order order) {
        this(order.getId(),  order.getProductId(), order.getProductQuantity(),
                order.getOrderDate(), order.getFinishDate());
    }
}
