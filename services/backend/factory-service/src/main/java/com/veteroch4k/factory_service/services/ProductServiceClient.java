package com.veteroch4k.factory_service.services;

import com.veteroch4k.factory_service.models.ProductDto;
import com.veteroch4k.factory_service.models.ProductManufacturingInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

  @GetMapping("/api/product/{id}/manufacturing-info")
  ProductManufacturingInfo getManufacturingInfo(@PathVariable("id") int id);



}
