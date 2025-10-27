package com.veteroch4k.toolwarehouse.repositories;

import com.veteroch4k.toolwarehouse.models.Tool;
import com.veteroch4k.toolwarehouse.models.ToolType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Long> {

  List<Tool> findToolsByToolType_Name(String type);

}
