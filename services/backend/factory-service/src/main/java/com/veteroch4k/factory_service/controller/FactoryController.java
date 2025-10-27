package com.veteroch4k.factory_service.controller;

import com.veteroch4k.factory_service.models.Factory;
import com.veteroch4k.factory_service.repository.FactoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/factory")
public class FactoryController {

  private final FactoryRepository factoryRepository;

  public FactoryController(FactoryRepository factoryRepository) {
    this.factoryRepository = factoryRepository;
  }

  @GetMapping
  public Page<Factory> getFactories(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size) {
    return factoryRepository.findAll(PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Factory> getFactory(@PathVariable long id) {
    //log.debug("Getting factory with id: {}", id);

    return factoryRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
