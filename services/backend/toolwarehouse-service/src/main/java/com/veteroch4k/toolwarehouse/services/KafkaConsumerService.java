package com.veteroch4k.toolwarehouse.services;

import com.veteroch4k.toolwarehouse.models.FactoryTools;
import com.veteroch4k.toolwarehouse.models.ToolReservedEvent;
import com.veteroch4k.toolwarehouse.models.commands.RequiredTools;
import com.veteroch4k.toolwarehouse.models.commands.ToolReservationCommand;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final ToolwarehouseService service;

  @KafkaListener(topics = "toolwarehouse-commands", groupId = "toolwarehouse-group")
  @Transactional
  public void handleToolsRequest(ToolReservationCommand command) {
    System.out.println("Получено сообщение kafka: Заказ ID: " + command.orderId());

    List<FactoryTools> tools = service.getFactoryTools(command.factoryId());

    Map<Integer, Integer> currentBalances = new HashMap<>();

    for(FactoryTools tool : tools) {
      currentBalances.put(tool.getToolType().getId(), tool.getQuantity());
    }

    for(RequiredTools requiredTool : command.tools()) {
      int currentAmount = currentBalances.getOrDefault(requiredTool.toolType(), 0);
      if(currentAmount < requiredTool.quantity()) {
        service.supplyTool(requiredTool.toolType(), requiredTool.quantity() - currentAmount, command.factoryId());
      }

      service.spendToolsForOrder(requiredTool.toolType(), requiredTool.quantity(), command.factoryId());
    }

    kafkaTemplate.send("toolwarehouse-events", new ToolReservedEvent(command.orderId()));


  }



}
