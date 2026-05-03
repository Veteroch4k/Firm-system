package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.models.FactoryMaterials;
import com.veteroch4k.warehouse.models.MaterialReservedEvent;
import com.veteroch4k.warehouse.models.commands.MaterialReservationCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @KafkaListener(topics = "warehouse-commands", groupId = "warehouse-group")
  public void handleMaterialRequest(MaterialReservationCommand command) {

    //  всё гуд!
    kafkaTemplate.send("warehouse-events", new MaterialReservedEvent(command.orderId()));
  }



}
