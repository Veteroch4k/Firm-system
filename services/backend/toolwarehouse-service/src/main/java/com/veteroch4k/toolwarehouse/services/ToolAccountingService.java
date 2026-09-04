package com.veteroch4k.toolwarehouse.services;

import com.veteroch4k.toolwarehouse.models.MovementType;
import com.veteroch4k.toolwarehouse.models.ToolAccounting;
import com.veteroch4k.toolwarehouse.repositories.ToolAccountingRepository;
import com.veteroch4k.toolwarehouse.repositories.ToolTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToolAccountingService {

    private final ToolAccountingRepository toolAccountingRepository;
    private final ToolTypeRepository toolTypeRepository;

    @Transactional
    public void supplyTool(Long toolTypeId, Long quantity, Long factoryId) {

        log.debug("Начало создания записи о поставке инструментов в фабрику ID: {}.", factoryId);

        ToolAccounting accounting = new ToolAccounting();
        accounting.setToolType(toolTypeRepository.getReferenceById(toolTypeId));
        accounting.setQuantity(quantity);
        accounting.setType(MovementType.INCOME);
        accounting.setFactoryId(factoryId);
        accounting.setEmployeeId(1L);
        accounting.setDate(LocalDate.now());

        toolAccountingRepository.save(accounting);

        log.debug("записи о поставке инструментов в фабрику ID: {} успешно создана", factoryId);

    }

    @Transactional
    public void spendToolsForOrder(Long toolTypeId, Long quantity, Long factoryId) {

        log.debug("Резервация инструментов фабрики ID: {} для заказа", factoryId);

        ToolAccounting accounting = new ToolAccounting();
        accounting.setToolType(toolTypeRepository.getReferenceById(toolTypeId));
        accounting.setQuantity(quantity);
        accounting.setType(MovementType.OUTCOME);
        accounting.setFactoryId(factoryId);
        accounting.setEmployeeId(1L);
        accounting.setDate(LocalDate.now());

        toolAccountingRepository.save(accounting);

        log.debug("Резервация инструментов фабрики ID: {} для заказа прошла успешно", factoryId);


    }

}
