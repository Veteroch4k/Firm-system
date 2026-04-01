package com.veteroch4k.factory_service.repository;

import com.veteroch4k.factory_service.models.OperationMaterials;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpMaterialsRepository extends JpaRepository<OperationMaterials, Integer> {

  List<OperationMaterials> getOperationMaterialsByOperation(int id);

}
