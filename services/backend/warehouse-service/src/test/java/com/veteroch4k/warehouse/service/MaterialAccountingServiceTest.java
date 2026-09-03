package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.models.Material;
import com.veteroch4k.warehouse.models.MaterialAccounting;
import com.veteroch4k.warehouse.models.MovementType;
import com.veteroch4k.warehouse.repositories.MaterialAccountingRepository;
import com.veteroch4k.warehouse.repositories.MaterialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MaterialAccountingServiceTest {

    @InjectMocks
    private MaterialAccountingService materialAccountingService;

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private MaterialAccountingRepository materialAccountingRepository;

    @Captor
    private ArgumentCaptor<MaterialAccounting> accountingCaptor;

    @Test
    void shouldSupplyMaterial() {
        Long materialId = 1L;
        Long quantity = 10L;
        Long factoryId = 2L;

        Material mockMaterial = new Material();
        mockMaterial.setId(materialId);

        when(materialRepository.getReferenceById(materialId)).thenReturn(mockMaterial);

        materialAccountingService.supplyMaterial(materialId, quantity, factoryId);

        verify(materialAccountingRepository).save(accountingCaptor.capture());

        MaterialAccounting savedAccounting = accountingCaptor.getValue();

        assertEquals(mockMaterial, savedAccounting.getMaterial());
        assertEquals(quantity, savedAccounting.getQuantity());
        assertEquals(MovementType.INCOME, savedAccounting.getType());
        assertEquals(factoryId, savedAccounting.getFactoryId());
        assertEquals(1L, savedAccounting.getEmployerId());
        assertEquals(LocalDate.now(), savedAccounting.getDate());
    }

    @Test
    void shouldSpendMaterialForOrder() {

        Long materialId = 1L;
        Long quantity = 10L;
        Long factoryId = 2L;

        Material mockMaterial = new Material();
        mockMaterial.setId(materialId);

        when(materialRepository.getReferenceById(materialId)).thenReturn(mockMaterial);

        materialAccountingService.spendMaterialForOrder(materialId, quantity, factoryId);

        verify(materialAccountingRepository).save(accountingCaptor.capture());

        MaterialAccounting savedAccounting = accountingCaptor.getValue();


        assertEquals(mockMaterial, savedAccounting.getMaterial());
        assertEquals(quantity, savedAccounting.getQuantity());
        assertEquals(MovementType.OUTCOME, savedAccounting.getType());
        assertEquals(factoryId, savedAccounting.getFactoryId());
        assertEquals(1L, savedAccounting.getEmployerId());
        assertEquals(LocalDate.now(), savedAccounting.getDate());

    }
}
