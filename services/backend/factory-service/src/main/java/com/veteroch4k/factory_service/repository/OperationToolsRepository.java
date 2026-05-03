package com.veteroch4k.factory_service.repository;

import com.veteroch4k.factory_service.models.OperationTools;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationToolsRepository extends JpaRepository<OperationTools, Long> {

  List<OperationTools> getOperationToolsByOperationToolsId(long id);

}
