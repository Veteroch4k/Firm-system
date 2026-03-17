package com.veteroch4k.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private static final String TOPIC_NAME = "order-events";

  public void sendOrderCreatedEvent(Long orderId) {
    String message = "Создан новый заказ с ID: " + orderId;
    kafkaTemplate.send(TOPIC_NAME, String.valueOf(orderId), message);
    System.out.println("Отправлено сообщение: " + message);
  }

}
