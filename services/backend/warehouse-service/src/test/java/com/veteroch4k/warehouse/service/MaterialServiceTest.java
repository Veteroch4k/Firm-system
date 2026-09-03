package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.dto.MaterialRequest;
import com.veteroch4k.warehouse.exceptions.ResourceNotFoundException;
import com.veteroch4k.warehouse.repositories.MaterialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MaterialServiceTest {

    @InjectMocks
    private MaterialService materialService;

    @Mock
    private MaterialRepository materialRepository;

    @Test
    void shouldThrowResourceNotFoundExceptionWhenFindMaterialById() {

        Long id = 1L;

        when(materialRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.findMaterialById(id)
        );

    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdateMaterialById() {

        Long id = 1L;
        MaterialRequest request = new MaterialRequest("Test");

        when(materialRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.updateMaterialById(id,request)
        );

    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeleteMaterialById() {

        Long id = 1L;

        when(materialRepository.existsById(id)).thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.deleteMaterialById(id)
        );

    }


}
