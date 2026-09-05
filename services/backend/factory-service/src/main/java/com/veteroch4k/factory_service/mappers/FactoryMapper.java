package com.veteroch4k.factory_service.mappers;

import com.veteroch4k.factory_service.dto.factory.FactoryResponse;
import com.veteroch4k.factory_service.dto.factory.FactoryOperationsResponse;
import com.veteroch4k.factory_service.models.Factory;
import com.veteroch4k.factory_service.models.Operation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FactoryMapper {

    FactoryResponse toFactoryResponse(Factory factory);

    FactoryOperationsResponse toFactoryOperationsResponse(Operation operation);


}
