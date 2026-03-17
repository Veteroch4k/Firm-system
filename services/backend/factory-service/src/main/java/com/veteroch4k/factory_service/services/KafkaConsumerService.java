package com.veteroch4k.factory_service.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

  @KafkaListener(topics = "order-events", groupId = "factory-group")
  public void listenOrderEvents(String message) {
    System.out.println("Получено сообщение kafka: " + message);

  }

}
