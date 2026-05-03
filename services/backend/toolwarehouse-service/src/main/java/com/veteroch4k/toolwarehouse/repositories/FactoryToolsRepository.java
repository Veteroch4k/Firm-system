package com.veteroch4k.toolwarehouse.repositories;

import com.veteroch4k.toolwarehouse.models.FactoryTools;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactoryToolsRepository extends JpaRepository<FactoryTools, Integer> {

  List<FactoryTools> findAllByFactoryId(int factoryId);

}
