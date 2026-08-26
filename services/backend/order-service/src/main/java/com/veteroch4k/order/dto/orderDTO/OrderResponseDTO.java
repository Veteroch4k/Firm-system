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
    // Думаю, что избыточно добавлять MapStruct для этого случая, мол, чтобы не было зависимости между DTO и БД
    // что мы тут ничего не должны знать про саму сущность, но, в рамках этого проекта, по моему мнению, это избыточно
    public OrderResponseDTO(Order order) {
        this(order.getId(),  order.getProductId(), order.getProductQuantity(),
                order.getOrderDate(), order.getFinishDate());
    }
}
