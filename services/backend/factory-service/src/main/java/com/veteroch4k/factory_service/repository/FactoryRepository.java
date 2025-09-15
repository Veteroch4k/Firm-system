package com.veteroch4k.factory_service.repository;

import com.veteroch4k.factory_service.models.Factory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactoryRepository extends JpaRepository<Factory, Long> {

}
