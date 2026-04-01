package com.veteroch4k.factory_service.services;

import com.veteroch4k.factory_service.models.FactoryOrder;
import com.veteroch4k.factory_service.models.OperationMaterials;
import com.veteroch4k.factory_service.models.OrderCreatedEvent;
import com.veteroch4k.factory_service.models.ProductDto;
import com.veteroch4k.factory_service.models.ProductManufacturingInfo;
import com.veteroch4k.factory_service.repository.FactoryOrderRepository;
import com.veteroch4k.factory_service.repository.OpMaterialsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final FactoryOrderRepository redisRepository;
  private final ProductServiceClient productServiceClient;
  private final OpMaterialsRepository opMaterialsRepository;

  @KafkaListener(topics = "order-events", groupId = "factory-group")
  public void listenOrderEvents(OrderCreatedEvent event) {
    System.out.println("Получено сообщение kafka: Заказ ID: " + event.orderId());

    FactoryOrder factoryOrder = new FactoryOrder(
        event.orderId(),
        event.productId(),
        event.productQuantity()
    );

    redisRepository.save(factoryOrder);

    System.out.println("Заказ " + event.orderId() + " успешно сохранен в Redis и готов к работе на фабрике!");

    ProductManufacturingInfo product = productServiceClient.getManufacturingInfo(event.productId());

    System.out.println("Получен заказ на производство продукта!: "+ product.description());

    // Здесь будет вызов складов для материалов
    List<OperationMaterials> opMaterials = opMaterialsRepository.getOperationMaterialsByOperation(product.)



  }

}
