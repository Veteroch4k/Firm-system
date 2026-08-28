package com.veteroch4k.product.repositories;

import com.veteroch4k.product.models.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawingRepository extends JpaRepository<Drawing, Long> {

}
