package com.veteroch4k.factory_service.repository;

import com.veteroch4k.factory_service.models.FactoryOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactoryOrderRepository extends CrudRepository<FactoryOrder, Long> {

}
