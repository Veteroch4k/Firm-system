package com.veteroch4k.factory_service.service;

import com.veteroch4k.factory_service.config.ProductFeignConfig;
import com.veteroch4k.factory_service.model.ProductManufacturingInfo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", configuration = {
        ProductFeignConfig.class
})
public interface ProductServiceClient {

  @Retry(name = "productService")
  @CircuitBreaker(name = "productService")
  @GetMapping("/api/product/{id}/manufacturing-info")
  ProductManufacturingInfo getManufacturingInfo(@PathVariable("id") Long id);



}
