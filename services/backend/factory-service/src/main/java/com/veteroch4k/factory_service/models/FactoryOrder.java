package com.veteroch4k.factory_service.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("OrderCache")
public class FactoryOrder {

  @Id
  private Long orderId;

  private Long productId;

  private Long productQuantity;

  private OrderStatus status;

  private boolean materialsReserved = false;
  private boolean toolsReserved = false;

}
