package com.veteroch4k.employers.controllers;

import com.veteroch4k.employers.models.Employer;
import com.veteroch4k.employers.repositories.EmployerRepository;
import com.veteroch4k.employers.services.EmployerSevice;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

  private final EmployerSevice service;
  private final EmployerRepository repository;

  public EmployerController(EmployerSevice service, EmployerRepository repository) {
    this.service = service;
    this.repository = repository;
  }


  @GetMapping
  public ResponseEntity<List<Employer>> getAllEmployers() {
    return ResponseEntity.ok(repository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employer> getEmployerById(@PathVariable Long id) {
    return repository.findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new RuntimeException("Employer not found with id: " + id)); // Обработка отсутствия сущности
  }

  @GetMapping("/random")
  public ResponseEntity<Employer> getRandomEmployer() {
    return service.getRandomEmployer()
        .map(ResponseEntity::ok)
        .orElseThrow();
  }
}