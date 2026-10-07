package com.veteroch4k.employers.repository;

import com.veteroch4k.employers.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}
