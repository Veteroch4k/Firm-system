package com.veteroch4k.product.controllers;

import com.veteroch4k.product.models.Product;
import com.veteroch4k.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductRepository productRepository;

  @GetMapping("/all")
  public Page<Product> getProducts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) {
    return productRepository.findAll(PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public Product getProduct(@PathVariable long id) {
    return productRepository
        .findById(id).orElse(new Product());

  }




}
