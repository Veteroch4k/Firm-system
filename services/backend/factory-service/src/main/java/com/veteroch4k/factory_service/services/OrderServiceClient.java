package com.veteroch4k.factory_service.services;

import com.veteroch4k.factory_service.configs.SecurityConfig;
import jakarta.persistence.Id;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", configuration = SecurityConfig.class)
public interface OrderServiceClient {

  @GetMapping("/api/order/operation/{orderId}")
  Long getOrderOperationId(@PathVariable("orderId") Integer orderId);



}
