package com.veteroch4k.toolwarehouse.service;

import com.veteroch4k.toolwarehouse.BaseIntegrationTest;
import com.veteroch4k.toolwarehouse.models.ToolReservedEvent;
import com.veteroch4k.toolwarehouse.models.commands.ToolReservationCommand;
import com.veteroch4k.toolwarehouse.services.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

public class KafkaConsumerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoBean
    private ReservationService reservationService;

    private final List<ToolReservedEvent> list = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "toolwarehouse-events", groupId = "test-group")
    private void kafkaListener(ToolReservedEvent reservedEvent) {
        list.add(reservedEvent);
    }

    @Test
    void shouldHandleToolsRequest() {

        Long orderId = 1L;

        ToolReservationCommand command = new ToolReservationCommand(
                orderId, List.of(), 2L
        );


        kafkaTemplate.send("toolwarehouse-commands", command);

        await().
                atMost(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(500))
                .untilAsserted(() ->
                        verify(reservationService).processReservation(command)
                );

        await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() ->
                        assertTrue(list.stream().anyMatch(e -> e.orderId().equals(orderId)))
                );


    }
}
