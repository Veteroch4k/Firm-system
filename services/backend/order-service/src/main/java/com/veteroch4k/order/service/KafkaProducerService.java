package com.veteroch4k.order.service;

import com.veteroch4k.order.model.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private static final String TOPIC_NAME = "order-events";

  public void sendOrderCreatedEvent(OrderCreatedEvent event) {
    String message = "Создан новый заказ с ID: " + event.orderId();
    kafkaTemplate.send(TOPIC_NAME, String.valueOf(event.orderId()), event);
    System.out.println("Отправлено сообщение: " + message);
  }

}
