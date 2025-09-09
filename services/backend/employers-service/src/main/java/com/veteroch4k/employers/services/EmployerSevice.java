package com.veteroch4k.employers.services;

import com.veteroch4k.employers.models.Employer;
import com.veteroch4k.employers.repositories.EmployerRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployerSevice {

  private EmployerRepository repository;

  public Employer getRandomEmployer() {

    long count =  repository.count();

    long idx = (int)(Math.random() * count);

    return repository.getEmployersById(idx);


  }

}
