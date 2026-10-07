package com.veteroch4k.toolwarehouse.repository;

import com.veteroch4k.toolwarehouse.model.FactoryTools;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactoryToolsRepository extends JpaRepository<FactoryTools, Long> {

  @EntityGraph(attributePaths = {"toolType"})
  List<FactoryTools> findAllByFactoryId(Long factoryId);

}
