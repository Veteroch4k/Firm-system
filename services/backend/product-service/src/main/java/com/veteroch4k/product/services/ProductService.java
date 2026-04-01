package com.veteroch4k.product.services;

import com.veteroch4k.product.models.Drawing;
import com.veteroch4k.product.models.Product;
import com.veteroch4k.product.models.ProductManufacturingInfo;
import com.veteroch4k.product.repositories.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public ProductManufacturingInfo getProductInfo(int id) {

    Product product = productRepository.findById(id).orElseGet(Product::new);

    Drawing drawing = Optional.ofNullable(product.getDrawing()).orElse(new Drawing(-14, -14, -14));

    return new ProductManufacturingInfo(
        product.getId(),
        product.getDescription(),
        drawing.getId(),
        drawing.getOperationId());
  }

}
