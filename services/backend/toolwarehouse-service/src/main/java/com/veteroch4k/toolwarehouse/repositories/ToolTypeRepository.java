package com.veteroch4k.toolwarehouse.repositories;

import com.veteroch4k.toolwarehouse.models.ToolType;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolTypeRepository extends JpaRepository<ToolType, Integer> {

}
