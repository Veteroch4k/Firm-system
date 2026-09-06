package com.veteroch4k.factory_service.service;

import com.veteroch4k.factory_service.exceptions.ResourceNotFoundException;
import com.veteroch4k.factory_service.mappers.FactoryMapper;
import com.veteroch4k.factory_service.repository.FactoryRepository;
import com.veteroch4k.factory_service.services.FactoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FactoryServiceTest {

    @InjectMocks
    private FactoryService factoryService;

    @Mock
    private FactoryRepository factoryRepository;

    @Mock
    private FactoryMapper mapper;


    @Test
    void shouldThrowResourceNotFoundExceptionWhenFindFactoryById() {

        Long id = 1L;

        when(factoryRepository.findFactoryById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> factoryService.findFactoryById(id));

    }

}
