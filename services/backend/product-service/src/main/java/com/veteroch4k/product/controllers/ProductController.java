package com.veteroch4k.product.controllers;

import com.veteroch4k.product.models.Drawing;
import com.veteroch4k.product.models.Product;
import com.veteroch4k.product.repositories.DrawingRepository;
import com.veteroch4k.product.repositories.ProductRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final DrawingRepository drawingRepository;
  private final ProductRepository productRepository;

  public ProductController(DrawingRepository drawingRepository, ProductRepository productRepository) {

    this.drawingRepository = drawingRepository;
    this.productRepository = productRepository;

  }


  @GetMapping
  public List<Product> getProducts() {
    return productRepository.findAll();
  }

  @GetMapping("/drawings")
  public List<Drawing> getDrawings() {
    return drawingRepository.findAll();
  }


}
