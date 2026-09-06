package com.veteroch4k.factory_service.services;

import com.veteroch4k.factory_service.exceptions.ResourceNotFoundException;
import com.veteroch4k.factory_service.models.*;
import com.veteroch4k.factory_service.models.commands.*;
import com.veteroch4k.factory_service.models.events.MaterialReservedEvent;
import com.veteroch4k.factory_service.models.events.OrderCreatedEvent;
import com.veteroch4k.factory_service.models.events.ToolReservedEvent;
import com.veteroch4k.factory_service.repository.FactoryOrderRepository;
import com.veteroch4k.factory_service.repository.OpMaterialsRepository;
import com.veteroch4k.factory_service.repository.OperationToolsRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final FactoryOrderRepository redisRepository;
    private final RedissonClient redissonClient;

    private final ProductServiceClient productServiceClient;
    private final OpMaterialsRepository opMaterialsRepository;
    private final OperationToolsRepository opToolsRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;


    // Получение заказа
    @KafkaListener(topics = "order-events", groupId = "factory-group")
    public void listenOrderEvents(OrderCreatedEvent event) {
        log.info("Получено сообщение kafka: Заказ ID: {}", event.orderId());

        // 1 - получаем инфу о товаре
        ProductManufacturingInfo product = productServiceClient.getManufacturingInfo(event.productId());
        log.info("Получен заказ на производство продукта: {}", product.description());


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
                OrderStatus.PENDING_RESOURCES,
                false, // Ждем материалы
                false  // Ждем инструменты
        );
        redisRepository.save(order);

        // 4. Отпрвляем команды на склады
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

        String lockKey = "order-lock:" + okEvent.orderId();
        RLock lock = redissonClient.getLock(lockKey);

        lock.lock();
        try {
            FactoryOrder order = redisRepository.findById(okEvent.orderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Заказ не найден: " + okEvent.orderId()));

            order.setMaterialsReserved(true);
            log.info("Получено сообщение kafka: Укомплектовали материалы для заказа {}", okEvent.orderId());
            checkIfReady(order);
        } finally {
            lock.unlock();
        }
    }

    // принятие инструментов
    @KafkaListener(topics = "toolwarehouse-events", groupId = "factory-group")
    public void handleToolResponse(ToolReservedEvent okEvent) {

        String lockKey = "order-lock:" + okEvent.orderId();
        RLock lock = redissonClient.getLock(lockKey);

        lock.lock();
        try {
            FactoryOrder order = redisRepository.findById(okEvent.orderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Заказ не найден: " + okEvent.orderId()));

            order.setToolsReserved(true);
            log.info("Получено сообщение kafka: Укомплектовали инструменты для заказа {}", okEvent.orderId());
            checkIfReady(order);
        } finally {
            lock.unlock();
        }
    }

    private void checkIfReady(FactoryOrder order) {
        if (order.isMaterialsReserved() && order.isToolsReserved()) {
            order.setStatus(OrderStatus.READY_FOR_PRODUCTION);
            redisRepository.save(order);
            log.info("Заказ {} укомплектован и готов к работе!", order.getOrderId());

            kafkaTemplate.send("employer-events", new SignOrderCommand(order.getOrderId()));


        } else {
            redisRepository.save(order);
        }
    }


}
