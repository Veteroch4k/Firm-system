package com.veteroch4k.product.controllers;

import com.veteroch4k.product.models.Drawing;
import com.veteroch4k.product.models.Product;
import com.veteroch4k.product.repositories.DrawingRepository;
import com.veteroch4k.product.repositories.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  private  ProductRepository productRepository;

  @GetMapping("/all")
  public Page<Product> getProducts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) {
    return productRepository.findAll(PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable long id) {
    return productRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }




}
