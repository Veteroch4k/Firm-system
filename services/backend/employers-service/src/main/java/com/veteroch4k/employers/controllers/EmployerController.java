package com.veteroch4k.employers.controllers;

import com.veteroch4k.employers.models.Employer;
import com.veteroch4k.employers.repositories.EmployerRepository;
import com.veteroch4k.employers.services.EmployerSevice;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


  @GetMapping("/all")
  public Page<Employer> getAllEmployers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) {
    return repository.findAll(PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employer> getEmployerById(@PathVariable Long id) {
    return repository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/random")
  public ResponseEntity<Employer> getRandomEmployer() {
    return service.getRandomEmployer()
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.noContent().build());
  }
}