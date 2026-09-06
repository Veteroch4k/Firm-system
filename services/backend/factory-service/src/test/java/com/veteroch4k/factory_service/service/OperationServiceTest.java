package com.veteroch4k.factory_service.service;

import com.veteroch4k.factory_service.dto.operation.OperationRequest;
import com.veteroch4k.factory_service.exceptions.ResourceNotFoundException;
import com.veteroch4k.factory_service.mappers.OperationMapper;
import com.veteroch4k.factory_service.models.Operation;
import com.veteroch4k.factory_service.repository.FactoryRepository;
import com.veteroch4k.factory_service.repository.OperationRepository;
import com.veteroch4k.factory_service.services.OperationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {

    @InjectMocks
    private OperationService operationService;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private FactoryRepository factoryRepository;

    @Mock
    private OperationMapper operationMapper;


    @Test
    void shouldThrowResourceNotFoundExceptionWhenFindOperationById() {

        Long id = 1L;

        when(operationRepository.findWithFactoryById(id)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> operationService.findOperationById(id));
        

    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenCreateOperation() {

        OperationRequest op = new OperationRequest(
                "test", 1L, 2L
        );

        when(factoryRepository.findById(op.factoryId())).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> operationService.createOperation(op));


    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdateOperation() {
        Long id = 3L;
        OperationRequest op = new OperationRequest(
                "test", 1L, 2L
        );

        when(operationRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> operationService.updateOperation(id, op));


    }

    @Test
    void shouldThrowResourceNotFoundExceptionFKWhenUpdateOperation() {
        Long id = 3L;
        OperationRequest op = new OperationRequest(
                "test", 1L, 2L
        );

        when(operationRepository.findById(id)).thenReturn(Optional.of(new Operation()));
        when(factoryRepository.existsById(op.factoryId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> operationService.updateOperation(id, op));


    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeleteOperation() {
        Long id = 3L;

        when(operationRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> operationService.deleteOperation(id));


    }
}
