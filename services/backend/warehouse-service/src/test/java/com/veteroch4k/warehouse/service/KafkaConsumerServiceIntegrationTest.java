package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.BaseIntegrationTest;
import com.veteroch4k.warehouse.models.MaterialReservedEvent;
import com.veteroch4k.warehouse.models.commands.MaterialReservationCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class KafkaConsumerServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoBean
    private ReservationService reservationService;

    private final List<MaterialReservedEvent> sentEvents = new CopyOnWriteArrayList<>();


    @KafkaListener(topics = "warehouse-events", groupId = "test-group")
    public void listenEvents(MaterialReservedEvent event) {
        sentEvents.add(event);
    }

    @Test
    void shouldConsumeCommandAndProduceEvent() {

        Long orderId = 15L;
        MaterialReservationCommand command = new MaterialReservationCommand(orderId,  List.of(),1L);

        kafkaTemplate.send("warehouse-commands", command);

        await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(500))
                .untilAsserted(() ->
                        verify(reservationService).processReservation(any(MaterialReservationCommand.class))
                );

        await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(() ->
                        assertTrue(sentEvents.stream().anyMatch(e -> e.orderId().equals(orderId)))
                );
    }

}
