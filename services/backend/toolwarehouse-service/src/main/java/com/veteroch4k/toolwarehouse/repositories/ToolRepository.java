package com.veteroch4k.toolwarehouse.repositories;

import com.veteroch4k.toolwarehouse.models.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, Long> {

  @EntityGraph(attributePaths = {"toolType"})
  Page<Tool> findAllByToolType_Name(String toolTypeName, Pageable pageable);

  @EntityGraph(attributePaths = {"toolType"})
  Page<Tool> findAll(Pageable pageable);

  @EntityGraph(attributePaths = {"toolType"})
  Optional<Tool> findToolById(Long id);

}
