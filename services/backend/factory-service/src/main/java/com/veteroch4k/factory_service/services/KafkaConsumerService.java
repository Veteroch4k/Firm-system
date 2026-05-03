package com.veteroch4k.factory_service.services;

import com.veteroch4k.factory_service.models.FactoryOrder;
import com.veteroch4k.factory_service.models.OperationMaterials;
import com.veteroch4k.factory_service.models.OperationTools;
import com.veteroch4k.factory_service.models.commands.MaterialReservationCommand;
import com.veteroch4k.factory_service.models.commands.RequiredMaterial;
import com.veteroch4k.factory_service.models.commands.RequiredTools;
import com.veteroch4k.factory_service.models.commands.ToolReservationCommand;
import com.veteroch4k.factory_service.models.events.MaterialReservedEvent;
import com.veteroch4k.factory_service.models.events.OrderCreatedEvent;
import com.veteroch4k.factory_service.models.ProductManufacturingInfo;
import com.veteroch4k.factory_service.models.events.ToolReservedEvent;
import com.veteroch4k.factory_service.repository.FactoryOrderRepository;
import com.veteroch4k.factory_service.repository.OpMaterialsRepository;
import com.veteroch4k.factory_service.repository.OperationToolsRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final FactoryOrderRepository redisRepository;
  private final ProductServiceClient productServiceClient;
  private final OpMaterialsRepository opMaterialsRepository;
  private final OperationToolsRepository opToolsRepository;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  // Получение заказа
  @KafkaListener(topics = "order-events", groupId = "factory-group")
  public void listenOrderEvents(OrderCreatedEvent event) {
    System.out.println("Получено сообщение kafka: Заказ ID: " + event.orderId());

    // 1 - получаем инфу о товаре
    ProductManufacturingInfo product = productServiceClient.getManufacturingInfo(event.productId());
    System.out.println("Получен заказ на производство продукта!: " + product.description());

    // 2 - получаем необходимые ресурсы
    List<OperationMaterials> opMaterials = opMaterialsRepository.getOperationMaterialsByOperationId(product.operationId());
    List<RequiredMaterial> requiredMaterials = opMaterials.stream()
        .map(opMat -> new RequiredMaterial(opMat.getMaterialId(), opMat.getQuantity()))
        .toList();

    List<OperationTools> opTools = opToolsRepository.getOperationToolsByOperationToolsIdOperationId(product.operationId());
    List<RequiredTools> requiredTools = opTools.stream()
        .map(opTool -> new RequiredTools(opTool.getOperationToolsId().getToolTypeId(), opTool.getQuantity()))
        .toList();


    // 3. Сохраняем начальное состояние в Redis
    FactoryOrder order = new FactoryOrder(
        event.orderId(),
        event.productId(),
        event.productQuantity(),
        "PENDING_RESOURCES",
        false, // Ждем материалы
        false  // Ждем инструменты
    );
    redisRepository.save(order);

    kafkaTemplate.send("warehouse-commands", new MaterialReservationCommand(event.orderId(), requiredMaterials,
        product.factoryId()));
    kafkaTemplate.send("toolwarehouse-commands", new ToolReservationCommand(event.orderId(), requiredTools,
        product.factoryId()));


    /**
     * Вызов летит на склад
     * Склад смотрит, хватает ли материалов у фабрики:
     * * 1 - Если не хватает, то делает запись о выделении фабрики материалов (инструментов)
     * * 2 - Дальше убавляем кол-во хранимых материалов (инструментов) у фабрики
     * Уведомление, что всё прошло успешно
     * Дальше остается только уведомить сотрудникаЮ, чтоб он сделал виртуальную подпись
     */


  }

  // принятие материалов
  @KafkaListener(topics = "warehouse-events", groupId = "factory-group")
  public void handleWarehouseResponse(MaterialReservedEvent okEvent) {
    FactoryOrder order = redisRepository.findById(okEvent.orderId()).orElseThrow();
    order.setMaterialsReserved(true);
    System.out.println("Получено сообщение kafka: Укомплектовали материалы!");
    checkIfReady(order);
  }

  // принятие инструментов
  @KafkaListener(topics = "toolwarehouse-events", groupId = "factory-group")
  public void handleToolResponse(ToolReservedEvent okEvent) {
    FactoryOrder order = redisRepository.findById(okEvent.orderId()).orElseThrow();
    order.setToolsReserved(true);
    System.out.println("Получено сообщение kafka: Укомплектовали инструменты!");
    checkIfReady(order);
  }

  private void checkIfReady(FactoryOrder order) {
    if (order.isMaterialsReserved() && order.isToolsReserved()) {
      order.setStatus("READY_FOR_PRODUCTION");
      redisRepository.save(order);
      System.out.println("Заказ " + order.getOrderId() + " укомплектован и готов к работе!");

      // kafkaTemplate.send("notification-events", new NotifyWorkerCommand(order.getOrderId()));
    } else {
      redisRepository.save(order);
    }
  }



}
