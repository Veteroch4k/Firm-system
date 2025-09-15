package com.veteroch4k.factory_service.repository;

import com.veteroch4k.factory_service.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {

}
