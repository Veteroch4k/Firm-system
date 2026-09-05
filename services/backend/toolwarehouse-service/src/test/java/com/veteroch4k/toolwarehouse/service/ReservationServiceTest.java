package com.veteroch4k.toolwarehouse.service;

import com.veteroch4k.toolwarehouse.models.FactoryTools;
import com.veteroch4k.toolwarehouse.models.ToolType;
import com.veteroch4k.toolwarehouse.models.commands.RequiredTools;
import com.veteroch4k.toolwarehouse.models.commands.ToolReservationCommand;
import com.veteroch4k.toolwarehouse.services.FactoryToolsService;
import com.veteroch4k.toolwarehouse.services.ReservationService;
import com.veteroch4k.toolwarehouse.services.ToolAccountingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private FactoryToolsService factoryToolsService;
    @Mock
    private ToolAccountingService toolAccountingService;

    @InjectMocks
    private ReservationService reservationService;

    @Captor
    private ArgumentCaptor<Long> factoryToolsCaptor;

    @Test
    void shouldProcessReservationWhenEnoughTool() {

        Long toolType1 = 2L;
        Long quantity1 = 6L;

        Long toolType2 = 3L;
        Long quantity2 = 4L;


        Long orderId = 1L;
        List<RequiredTools> tools = List.of(new RequiredTools(toolType1, quantity1), new RequiredTools(toolType2, quantity2));
        Long factoryId = 5L;

        ToolReservationCommand command = new ToolReservationCommand(
                orderId, tools, factoryId
        );


        /* */


        ToolType toolType1_1 = new ToolType();
        toolType1_1.setId(toolType1);

        FactoryTools factoryTools1 = new FactoryTools();
        factoryTools1.setToolType(toolType1_1);
        factoryTools1.setQuantity(quantity1);


        ToolType toolType2_2 = new ToolType();
        toolType2_2.setId(toolType2);

        FactoryTools factoryTools2 = new FactoryTools();
        factoryTools2.setToolType(toolType2_2);
        factoryTools2.setQuantity(quantity2);


        when(factoryToolsService.getFactoryTools(command.factoryId())).thenReturn(List.of(factoryTools1, factoryTools2));

        reservationService.processReservation(command);

        verify(toolAccountingService, never()).supplyTool(any(), any(), any());

        verify(toolAccountingService, times(2)).spendToolsForOrder(factoryToolsCaptor.capture(), factoryToolsCaptor.capture(), factoryToolsCaptor.capture());

        List<Long> values = factoryToolsCaptor.getAllValues();

        assertEquals(values.getFirst(),toolType1);
        assertEquals(values.get(1),quantity1);
        assertEquals(values.get(2),factoryId);

        assertEquals(values.get(3),toolType2);
        assertEquals(values.get(4),quantity2);
        assertEquals(values.get(5),factoryId);

    }

    @Test
    void shouldProcessReservationWhenNotEnoughTool() {

        Long toolType1 = 2L;
        Long quantity1 = 10L;

        Long toolType2 = 3L;
        Long quantity2 = 5L;


        Long orderId = 1L;
        List<RequiredTools> tools = List.of(new RequiredTools(toolType1, quantity1), new RequiredTools(toolType2, quantity2));
        Long factoryId = 5L;

        ToolReservationCommand command = new ToolReservationCommand(
                orderId, tools, factoryId
        );


        /* */

        Long notEnoughType1 = 6L;
        Long notEnoughType2 = 4L;



        ToolType toolType1_1 = new ToolType();
        toolType1_1.setId(toolType1);

        FactoryTools factoryTools1 = new FactoryTools();
        factoryTools1.setToolType(toolType1_1);
        factoryTools1.setQuantity(notEnoughType1);


        ToolType toolType2_2 = new ToolType();
        toolType2_2.setId(toolType2);

        FactoryTools factoryTools2 = new FactoryTools();
        factoryTools2.setToolType(toolType2_2);
        factoryTools2.setQuantity(notEnoughType2);


        when(factoryToolsService.getFactoryTools(command.factoryId())).thenReturn(List.of(factoryTools1, factoryTools2));

        reservationService.processReservation(command);

        verify(toolAccountingService,times(2)).supplyTool(factoryToolsCaptor.capture(), factoryToolsCaptor.capture(), factoryToolsCaptor.capture());

        verify(toolAccountingService, times(2)).spendToolsForOrder(factoryToolsCaptor.capture(), factoryToolsCaptor.capture(), factoryToolsCaptor.capture());

        List<Long> values = factoryToolsCaptor.getAllValues();

        assertEquals(values.getFirst(),toolType1);
        assertEquals(values.get(1),quantity1 - notEnoughType1);
        assertEquals(values.get(2),factoryId);

        assertEquals(values.get(3),toolType2);
        assertEquals(values.get(4),quantity2 - notEnoughType2);
        assertEquals(values.get(5),factoryId);


        assertEquals(values.get(6),toolType1);
        assertEquals(values.get(7),quantity1);
        assertEquals(values.get(8),factoryId);

        assertEquals(values.get(9),toolType2);
        assertEquals(values.get(10),quantity2);
        assertEquals(values.get(11),factoryId);

    }

}
