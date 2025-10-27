package com.veteroch4k.employers.repositories;

import com.veteroch4k.employers.models.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}
