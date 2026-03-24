package com.veteroch4k.factory_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("OrderCache")
public class FactoryOrder {

  @Id
  private Long orderId;

  private Long operationId;

  private Integer productQuantity;

}
