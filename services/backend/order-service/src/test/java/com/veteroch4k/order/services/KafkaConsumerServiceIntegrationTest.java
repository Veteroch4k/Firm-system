package com.veteroch4k.order.services;

import com.veteroch4k.order.BaseIntegrationTest;
import com.veteroch4k.order.model.event.OrderReadyEvent;
import com.veteroch4k.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

public class KafkaConsumerServiceIntegrationTest extends BaseIntegrationTest {

    @MockitoBean
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @Test
    void shouldListenOnOrderEvents() {

        Long orderId = 1L;

        OrderReadyEvent command = new OrderReadyEvent(orderId);

        kafkaTemplate.send("order-ready-events", command);

        await()
                .atMost(Duration.ofSeconds(5))
                .pollDelay(Duration.ofMillis(500))
                .untilAsserted(() ->

                        verify(orderRepository).findById(orderId)

                );


    }
}
