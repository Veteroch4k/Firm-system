package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.models.FactoryMaterials;
import com.veteroch4k.warehouse.models.Material;
import com.veteroch4k.warehouse.models.commands.MaterialReservationCommand;
import com.veteroch4k.warehouse.models.commands.RequiredMaterial;
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

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private FactoryMaterialService factoryMaterialService;

    @Mock
    private MaterialAccountingService accountingService;

    @Captor
    private ArgumentCaptor<Long> materialCaptor;

    @Test
    void shouldProcessReservationWhenHavingEnoughMaterials() {

        /* Указываем необходимые для заказа материалы и их количество */

        Long materialId1 = 1L;
        Long quantity1 = 3L;
        RequiredMaterial material1 = new RequiredMaterial(materialId1,quantity1);

        Long materialId2 = 2L;
        Long quantity2 = 6L;
        RequiredMaterial material2 = new RequiredMaterial(materialId2,quantity2);

        /* Создаем команду создания заказа */

        Long orderId = 1L;
        List<RequiredMaterial > materials = List.of(material1, material2);
        Long factoryId = 2L;

        MaterialReservationCommand command = new MaterialReservationCommand(
                orderId, materials, factoryId

        );

        /* Устанавливаем сколько материалов уже присутствовало на фабрике */

        Long materialQuantity1 = 5L;
        Material fMaterial1 = new Material();
        fMaterial1.setId(materialId1);


        FactoryMaterials factoryMaterials1 = new FactoryMaterials();
        factoryMaterials1.setMaterial(fMaterial1);
        factoryMaterials1.setQuantity(materialQuantity1);


        Long materialQuantity2 = 10L;
        Material fMaterial2 = new Material();
        fMaterial2.setId(materialId2);

        FactoryMaterials factoryMaterials2 = new FactoryMaterials();
        factoryMaterials2.setMaterial(fMaterial2);
        factoryMaterials2.setQuantity(materialQuantity2);

        when(factoryMaterialService.getFactoryMaterials(command.factoryId())).thenReturn(
                List.of(factoryMaterials1, factoryMaterials2)
        );

        /* Актим */

        reservationService.processReservation(command);


        // Запроса на пополнение материалов ни разу не было
        verify(accountingService, never()).supplyMaterial(any(), any(), any());

        // Запроса на трату материалов два раза
        verify(accountingService, times(2)).spendMaterialForOrder(materialCaptor.capture(), materialCaptor.capture(), any());

        List<Long> capturedArguments = materialCaptor.getAllValues();

        assertEquals(capturedArguments.getFirst(), material1.materialId());
        assertEquals(capturedArguments.get(1),quantity1);

        assertEquals(capturedArguments.get(2), material2.materialId());
        assertEquals(capturedArguments.get(3), quantity2);

    }

    @Test
    void shouldProcessReservationWhenHavingNotEnoughMaterials() {

        /* Указываем необходимые для заказа материалы и их количество */

        Long materialId1 = 1L;
        Long quantity1 = 5L;
        RequiredMaterial material1 = new RequiredMaterial(materialId1,quantity1);

        Long materialId2 = 2L;
        Long quantity2 = 10L;
        RequiredMaterial material2 = new RequiredMaterial(materialId2,quantity2);

        /* Создаем команду создания заказа */

        Long orderId = 1L;
        List<RequiredMaterial > materials = List.of(material1, material2);
        Long factoryId = 2L;

        MaterialReservationCommand command = new MaterialReservationCommand(
                orderId, materials, factoryId

        );

        /* Устанавливаем сколько материалов уже присутствовало на фабрике */

        Long materialQuantity1 = 3L;
        Material fMaterial1 = new Material();
        fMaterial1.setId(materialId1);


        FactoryMaterials factoryMaterials1 = new FactoryMaterials();
        factoryMaterials1.setMaterial(fMaterial1);
        factoryMaterials1.setQuantity(materialQuantity1);


        Long materialQuantity2 = 6L;
        Material fMaterial2 = new Material();
        fMaterial2.setId(materialId2);

        FactoryMaterials factoryMaterials2 = new FactoryMaterials();
        factoryMaterials2.setMaterial(fMaterial2);
        factoryMaterials2.setQuantity(materialQuantity2);

        /* Устанавливаем, сколько будет зарезервировано */
        Long remainingMaterial1 = quantity1 - materialQuantity1;
        Long remainingMaterial2 = quantity2 - materialQuantity2;


        when(factoryMaterialService.getFactoryMaterials(command.factoryId())).thenReturn(
                List.of(factoryMaterials1, factoryMaterials2)
        );

        /* Актим */

        reservationService.processReservation(command);

        /* Запрос на пополнение материалов 2 раза */

        verify(accountingService, times(2)).supplyMaterial(materialCaptor.capture(), materialCaptor.capture(), any());

        /* Запрос на трату материалов 2 раза */

        verify(accountingService, times(2)).spendMaterialForOrder(materialCaptor.capture(), materialCaptor.capture(), any());

        List<Long> capturedArguments = materialCaptor.getAllValues();

        /* Поставка */

        assertEquals(capturedArguments.getFirst(), material1.materialId());
        assertEquals(capturedArguments.get(1),remainingMaterial1);

        assertEquals(capturedArguments.get(2), material2.materialId());
        assertEquals(capturedArguments.get(3), remainingMaterial2);

        /* Трата */

        assertEquals(capturedArguments.get(4), material1.materialId());
        assertEquals(capturedArguments.get(5),quantity1);

        assertEquals(capturedArguments.get(6), material2.materialId());
        assertEquals(capturedArguments.get(7), quantity2);


    }
}
