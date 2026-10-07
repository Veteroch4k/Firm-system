package com.veteroch4k.warehouse.repository;

import com.veteroch4k.warehouse.model.FactoryMaterials;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactoryMaterialsRepository extends JpaRepository<FactoryMaterials, Long> {

  @EntityGraph(attributePaths = {"material"})
  List<FactoryMaterials> findAllByFactoryId(Long id);



}
