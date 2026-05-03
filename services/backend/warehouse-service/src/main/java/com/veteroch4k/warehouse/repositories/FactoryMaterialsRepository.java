package com.veteroch4k.warehouse.repositories;

import com.veteroch4k.warehouse.models.FactoryMaterials;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactoryMaterialsRepository extends JpaRepository<FactoryMaterials, Integer> {

  List<FactoryMaterials> findAllByFactoryId(int id);

}
