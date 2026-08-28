package com.veteroch4k.product.repositories;

import com.veteroch4k.product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @EntityGraph(attributePaths = {"drawing"})
    Optional<Product> findById(Long id);

    @EntityGraph(attributePaths = {"drawing"})
    Page<Product> findAll(Pageable pageable);

}
