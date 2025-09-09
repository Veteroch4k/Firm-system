package com.veteroch4k.employers.repositories;

import com.veteroch4k.employers.models.Employer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

  Employer getEmployersById(long id );



}
