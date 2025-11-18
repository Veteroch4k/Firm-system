package com.veteroch4k.factory_service.controller;

import com.veteroch4k.factory_service.models.Factory;
import com.veteroch4k.factory_service.models.Operation;
import com.veteroch4k.factory_service.repository.OperationRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operation")
public class OperationController {

  private  final OperationRepository operationRepository;

  public OperationController(OperationRepository operationRepository) {
    this.operationRepository = operationRepository;
  }


  @GetMapping("/all")
  public Page<Operation> operations(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size) {
    return operationRepository.findAll(PageRequest.of(page, size));
  }

  @GetMapping("/operation/{id}")
  public ResponseEntity<Operation> operationById(@PathVariable int id) {

    return operationRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());

  }

  @PostMapping("/create-operation")
  @ResponseStatus(HttpStatus.CREATED)
  public Operation createOperation(@RequestBody  Operation op) {

    return operationRepository.save(op);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Operation> updateOpearation(
      @PathVariable int id,
      @RequestBody Operation operation)
  {
    if(!operationRepository.existsById(id)) return ResponseEntity.notFound().build();

    operation.setId(id);

    Operation updated = operationRepository.save(operation);

    return ResponseEntity.ok(updated);


  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOperation(@PathVariable int id) {
    operationRepository.deleteById(id);
  }




}
