package com.veteroch4k.factory_service.controller;

import com.veteroch4k.factory_service.models.Factory;
import com.veteroch4k.factory_service.models.Operation;
import com.veteroch4k.factory_service.repository.FactoryRepository;
import com.veteroch4k.factory_service.repository.OperationRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/factories")
public class FactoryController {

  private FactoryRepository factoryRepository;
  private OperationRepository operationRepository;

  @GetMapping
  public ResponseEntity<List<Factory>> factories() {
    return ResponseEntity.ok(factoryRepository.findAll());
  }

  @GetMapping("/operations")
  public ResponseEntity<List<Operation>> operations() {
    return ResponseEntity.ok(operationRepository.findAll());
  }

  @GetMapping("/operation/{id}")
  public ResponseEntity<Operation> operationById(@PathVariable long id) {

    return operationRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());

  }

}
