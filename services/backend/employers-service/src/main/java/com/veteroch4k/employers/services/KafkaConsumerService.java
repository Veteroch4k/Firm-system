package com.veteroch4k.employers.services;

import com.veteroch4k.employers.models.events.OrderReadyEvent;
import com.veteroch4k.employers.models.commands.SignOrderCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final EmployerSignService employerSignService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "employer-events", groupId = "employer-group")
    public void listenOnOrderEvents(SignOrderCommand command) {
        log.info("Принят заказ на виртуальную подпись");

        employerSignService.processSign(command);

        log.info("Подпись успешно поставлена");

        kafkaTemplate.send("order-ready-events", new OrderReadyEvent(command.orderId()));


    }
}
