package com.veteroch4k.product.repositories;

import com.veteroch4k.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
