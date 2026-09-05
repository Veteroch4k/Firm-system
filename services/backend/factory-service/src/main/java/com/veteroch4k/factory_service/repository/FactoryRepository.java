package com.veteroch4k.factory_service.repository;

import com.veteroch4k.factory_service.models.Factory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FactoryRepository extends JpaRepository<Factory, Long> {

    @Query("SELECT f.id FROM Factory f")
    Page<Long> findFactoryIds(Pageable pageable);

    @EntityGraph(attributePaths = {"operations"})
    List<Factory> findFactoryByIdIn(List<Long> ids);

    @EntityGraph(attributePaths = {"operation"})
    Optional<Factory> findFactoryById(Long id);

}
