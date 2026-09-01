package com.veteroch4k.warehouse.repositories;

import com.veteroch4k.warehouse.models.Material;
import com.veteroch4k.warehouse.models.MaterialAccounting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialAccountingRepository extends JpaRepository<MaterialAccounting, Long> {


}
