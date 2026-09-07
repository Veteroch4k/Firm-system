package com.veteroch4k.employers.service;

import com.veteroch4k.employers.BaseIntegrationTest;
import com.veteroch4k.employers.models.commands.SignOrderCommand;
import com.veteroch4k.employers.services.EmployerSignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.doNothing;

public class KafkaConsumerServiceIntegrationTests extends BaseIntegrationTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoBean
    private EmployerSignService signService;

    private boolean flag = false;

    @KafkaListener(topics = "order-ready-events", groupId = "test-group")
    public void listenEvents() {
        flag = true;
    }

    @Test
    public void shouldListenOnOrderEvents() {

        Long orderId = 1L;

        SignOrderCommand command = new SignOrderCommand(orderId);

        doNothing()
                .when(signService).processSign(command);

        kafkaTemplate.send("employer-events", command);

        await()
                .atMost(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(500))
                .until(() -> flag);

    }
}
