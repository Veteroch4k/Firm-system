package com.veteroch4k.toolwarehouse.services;

import com.veteroch4k.toolwarehouse.models.FactoryTools;
import com.veteroch4k.toolwarehouse.models.ToolAccounting;
import com.veteroch4k.toolwarehouse.models.commands.RequiredTools;
import com.veteroch4k.toolwarehouse.models.commands.ToolReservationCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final FactoryToolsService factoryToolsService;
    private final ToolAccountingService toolAccountingService;

    @Transactional
    public void processReservation(ToolReservationCommand command) {

        log.info("Начало резервации инструментов для заказа ID: {}", command.orderId());


        List<FactoryTools> tools = factoryToolsService.getFactoryTools(command.factoryId());

        Map<Long, Long> currentBalances = new HashMap<>();

        for(FactoryTools tool : tools) {
            currentBalances.put(tool.getToolType().getId(), tool.getQuantity());
        }

        for(RequiredTools requiredTool : command.tools()) {
            long currentAmount = currentBalances.getOrDefault(requiredTool.toolType(), 0L);
            if(currentAmount < requiredTool.quantity()) {
                toolAccountingService.supplyTool(requiredTool.toolType(), requiredTool.quantity() - currentAmount, command.factoryId());
            }

            toolAccountingService.spendToolsForOrder(requiredTool.toolType(), requiredTool.quantity(), command.factoryId());
        }

        log.info("Резервации инструментов для заказа ID: {} прошла успешно", command.orderId());


    }
}
