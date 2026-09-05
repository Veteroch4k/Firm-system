package com.veteroch4k.factory_service.repository;

import com.veteroch4k.factory_service.models.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @EntityGraph(attributePaths = {"factory"})
    @Query("SELECT o FROM Operation o")
    Page<Operation> findAllWithFactory(Pageable pageable);

    @EntityGraph(attributePaths = {"factory"})
    Optional<Operation> findWithFactoryById(Long id);

}
