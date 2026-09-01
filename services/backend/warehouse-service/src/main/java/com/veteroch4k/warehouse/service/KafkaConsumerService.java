package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.models.FactoryMaterials;
import com.veteroch4k.warehouse.models.Material;
import com.veteroch4k.warehouse.models.MaterialReservedEvent;
import com.veteroch4k.warehouse.models.commands.MaterialReservationCommand;
import com.veteroch4k.warehouse.models.commands.RequiredMaterial;
import com.veteroch4k.warehouse.repositories.FactoryMaterialsRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final ReservationService reservationService;


  @KafkaListener(topics = "warehouse-commands", groupId = "warehouse-group")
  public void handleMaterialRequest(MaterialReservationCommand command) {
    System.out.println("Получено сообщение kafka: Заказ ID: " + command.orderId());


    reservationService.processReservation(command);

    kafkaTemplate.send("warehouse-events", new MaterialReservedEvent(command.orderId()));

  }



}
