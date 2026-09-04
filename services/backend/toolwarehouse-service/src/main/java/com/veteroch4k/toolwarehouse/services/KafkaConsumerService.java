package com.veteroch4k.toolwarehouse.services;

import com.veteroch4k.toolwarehouse.models.FactoryTools;
import com.veteroch4k.toolwarehouse.models.ToolReservedEvent;
import com.veteroch4k.toolwarehouse.models.commands.RequiredTools;
import com.veteroch4k.toolwarehouse.models.commands.ToolReservationCommand;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private final ReservationService reservationService;

  @KafkaListener(topics = "toolwarehouse-commands", groupId = "toolwarehouse-group")
  @Transactional
  public void handleToolsRequest(ToolReservationCommand command) {
    log.info("Получено сообщение kafka: Заказ ID: {}",command.orderId());

    reservationService.processReservation(command);

    log.info("Отправка сообщения об успешной резервации материалов.");

    kafkaTemplate.send("toolwarehouse-events", new ToolReservedEvent(command.orderId()));


  }



}
