package com.veteroch4k.toolwarehouse.service;

import com.veteroch4k.toolwarehouse.models.MovementType;
import com.veteroch4k.toolwarehouse.models.ToolAccounting;
import com.veteroch4k.toolwarehouse.models.ToolType;
import com.veteroch4k.toolwarehouse.repositories.ToolAccountingRepository;
import com.veteroch4k.toolwarehouse.repositories.ToolTypeRepository;
import com.veteroch4k.toolwarehouse.services.ToolAccountingService;
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
public class ToolAccountingServiceTest {


    @InjectMocks
    private ToolAccountingService toolAccountingService;

    @Mock
    private ToolAccountingRepository toolAccountingRepository;

    @Mock
    private ToolTypeRepository toolTypeRepository;

    @Captor
    private ArgumentCaptor<ToolAccounting> toolAccountingCaptor;

    @Test
    void shouldSupplyTool() {
        Long toolTypeId = 1L;
        Long quantity = 10L;
        Long factoryId = 2L;

        ToolType toolType = new ToolType();
        toolType.setId(toolTypeId);

        when(toolTypeRepository.getReferenceById(toolTypeId)).thenReturn(toolType);

        toolAccountingService.supplyTool(toolTypeId, quantity, factoryId);

        verify(toolAccountingRepository).save(toolAccountingCaptor.capture());

        ToolAccounting toolAccounting = toolAccountingCaptor.getValue();

        assertEquals(toolAccounting.getToolType().getId(), toolTypeId);
        assertEquals(toolAccounting.getQuantity(), quantity);
        assertEquals(toolAccounting.getType(), MovementType.INCOME);
        assertEquals(toolAccounting.getFactoryId(), factoryId);
        assertEquals(toolAccounting.getEmployeeId(), 1L);
        assertEquals(toolAccounting.getDate(), LocalDate.now());


    }

    @Test
    void shouldSpendTool() {
        Long toolTypeId = 1L;
        Long quantity = 10L;
        Long factoryId = 2L;

        ToolType toolType = new ToolType();
        toolType.setId(toolTypeId);

        when(toolTypeRepository.getReferenceById(toolTypeId)).thenReturn(toolType);

        toolAccountingService.spendToolsForOrder(toolTypeId, quantity, factoryId);

        verify(toolAccountingRepository).save(toolAccountingCaptor.capture());

        ToolAccounting toolAccounting = toolAccountingCaptor.getValue();

        assertEquals(toolAccounting.getToolType().getId(), toolTypeId);
        assertEquals(toolAccounting.getQuantity(), quantity);
        assertEquals(toolAccounting.getType(), MovementType.OUTCOME);
        assertEquals(toolAccounting.getFactoryId(), factoryId);
        assertEquals(toolAccounting.getEmployeeId(), 1L);
        assertEquals(toolAccounting.getDate(), LocalDate.now());


    }


}
