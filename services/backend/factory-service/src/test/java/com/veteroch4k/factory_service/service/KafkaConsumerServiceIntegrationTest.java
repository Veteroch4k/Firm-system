package com.veteroch4k.factory_service.service;

import com.veteroch4k.factory_service.BaseIntegrationTest;
import com.veteroch4k.factory_service.models.*;
import com.veteroch4k.factory_service.models.commands.MaterialReservationCommand;
import com.veteroch4k.factory_service.models.commands.SignOrderCommand;
import com.veteroch4k.factory_service.models.commands.ToolReservationCommand;
import com.veteroch4k.factory_service.models.events.MaterialReservedEvent;
import com.veteroch4k.factory_service.models.events.OrderCreatedEvent;
import com.veteroch4k.factory_service.models.events.ToolReservedEvent;
import com.veteroch4k.factory_service.repository.FactoryOrderRepository;
import com.veteroch4k.factory_service.repository.OpMaterialsRepository;
import com.veteroch4k.factory_service.repository.OperationToolsRepository;
import com.veteroch4k.factory_service.services.ProductServiceClient;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class KafkaConsumerServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final List<Long> flag = new CopyOnWriteArrayList<>();

    @Autowired
    private FactoryOrderRepository redisRepository;

    @Autowired
    private RedissonClient redissonClient;

    @MockitoBean
    private ProductServiceClient productServiceClient;

    @MockitoBean
    private OpMaterialsRepository opMaterialsRepository;

    @MockitoBean
    private OperationToolsRepository opToolsRepository;


    @KafkaListener(topics = "employer-events", groupId = "test-group-1")
    public void listenOrder(SignOrderCommand command) {
        flag.add(command.orderId());
    }

    @KafkaListener(topics = "warehouse-commands", groupId = "test-group-2")
    public void listenMaterial(MaterialReservationCommand command) {
        flag.add(command.orderId());
        kafkaTemplate.send("warehouse-events", new MaterialReservedEvent(command.orderId()));
    }

    @KafkaListener(topics = "toolwarehouse-commands", groupId = "test-group-3")
    public void listenTool(ToolReservationCommand command) {
        flag.add(command.orderId());
        kafkaTemplate.send("toolwarehouse-events", new ToolReservedEvent(command.orderId()));
    }


    @Test
    void shouldListenOrderEvents() {
        Long orderId = 1L;
        Long productId = 2L;
        Long productQuantity = 10L;

        OrderCreatedEvent event = new OrderCreatedEvent(
                orderId, productId, productQuantity
        );

        ProductManufacturingInfo product = new ProductManufacturingInfo(
                productId, "Test", 1L, 1L, 1L
        );

        when(productServiceClient.getManufacturingInfo(event.productId())).thenReturn(product);

        OperationMaterials material = new OperationMaterials();
        material.setMaterialId(1L);
        material.setQuantity(5L);

        when(opMaterialsRepository.getOperationMaterialsByOperationId(product.operationId())).thenReturn(List.of(material));

        OperationTools tool = new OperationTools();
        OperationToolsId operationId = new OperationToolsId();
        operationId.setToolTypeId(7L);
        tool.setOperationToolsId(operationId);
        tool.setQuantity(6L);

        when(opToolsRepository.getOperationToolsByOperationToolsIdOperationId(product.operationId())).thenReturn(List.of(tool));


        kafkaTemplate.send("order-events", event);

        await()
                .atMost(Duration.ofSeconds(5))
                .pollDelay(Duration.ofMillis(500))
                .untilAsserted(() -> {
                    assertTrue(flag.stream().allMatch(pr -> pr.equals(orderId)));
                    assertEquals(3, flag.size());
                });
        await()
                .atMost(Duration.ofSeconds(5))
                .pollDelay(Duration.ofMillis(500))
                .untilAsserted(() ->
                        assertEquals(redisRepository.findById(orderId).get().getStatus(), OrderStatus.READY_FOR_PRODUCTION)
                );

    }

}
