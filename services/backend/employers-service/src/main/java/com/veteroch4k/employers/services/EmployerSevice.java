package com.veteroch4k.employers.services;

import com.veteroch4k.employers.models.Employer;
import com.veteroch4k.employers.repositories.EmployerRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmployerSevice {

  private EmployerRepository repository;

  public EmployerSevice(EmployerRepository repository) {
    this.repository = repository;
  }

  public Optional<Employer> getRandomEmployer() {
    long count = repository.count();
    if (count == 0) {
      return Optional.empty();
    }

    long idx = (long)(Math.random() * count);
    return repository.findById(idx);
  }

}
