package com.veteroch4k.factory_service.mapper;

import com.veteroch4k.factory_service.dto.operation.OperationFactoryResponse;
import com.veteroch4k.factory_service.dto.operation.OperationRequest;
import com.veteroch4k.factory_service.dto.operation.OperationResponse;
import com.veteroch4k.factory_service.model.Factory;
import com.veteroch4k.factory_service.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    OperationResponse toOperationResponse(Operation operation);

    OperationFactoryResponse toOperationFactoryResponse(Factory factory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "factory", ignore = true)
    Operation toEntity(OperationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "factory", ignore = true)
    void updateEntityFromRequest(OperationRequest request, @MappingTarget Operation entity);
}
