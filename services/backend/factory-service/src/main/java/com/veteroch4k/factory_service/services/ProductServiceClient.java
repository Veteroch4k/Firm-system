package com.veteroch4k.factory_service.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

  @GetMapping("/api/product/all")
  Long getStubTest();

  @GetMapping("/api/order/operation/{orderId}")
  Long getOrderOperationId(@PathVariable("orderId") Long orderId);

}
