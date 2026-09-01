package com.veteroch4k.warehouse.repositories;

import com.veteroch4k.warehouse.models.Material;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {


}
