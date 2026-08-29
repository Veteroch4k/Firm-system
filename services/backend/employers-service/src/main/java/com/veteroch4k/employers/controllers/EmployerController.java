package com.veteroch4k.employers.controllers;

import com.veteroch4k.employers.dto.EmployerResponse;
import com.veteroch4k.employers.models.Employer;
import com.veteroch4k.employers.repositories.EmployerRepository;
import com.veteroch4k.employers.services.EmployerSevice;
import java.util.List;
import java.util.concurrent.TimeoutException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employers")
public class EmployerController {

  private final EmployerSevice service;


  @GetMapping("/all")
  public Page<EmployerResponse> getAllEmployers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) {
    return service.findAllEmployers(PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public EmployerResponse getEmployerById(@PathVariable Long id) {
    return service.findEmployerById(id);
  }

  @GetMapping("/random")
  public EmployerResponse getRandomEmployer() {
    return service.getRandomEmployer();
  }

  @GetMapping("/test")
  public ResponseEntity<String> test() throws TimeoutException {
    return service.immitateSomeWork();
  }
}