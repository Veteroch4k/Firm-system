package com.veteroch4k.product.services;

import com.veteroch4k.product.models.Drawing;
import com.veteroch4k.product.models.Product;
import com.veteroch4k.product.models.ProductManufacturingInfo;
import com.veteroch4k.product.repositories.DrawingRepository;
import com.veteroch4k.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private ProductRepository productRepository;
  private DrawingRepository drawingRepository;

  public ProductManufacturingInfo getProductInfo(int id) {

    Product product = productRepository.findById(id).get();

    Drawing drawing = drawingRepository.findById(product.getDrawing()).get();

  }

}
