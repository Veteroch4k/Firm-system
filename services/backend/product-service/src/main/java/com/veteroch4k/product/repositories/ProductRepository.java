package com.veteroch4k.product.repositories;

import com.veteroch4k.product.models.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @EntityGraph(attributePaths = {"drawing"})
    Optional<Product> findById(Long id);

}
