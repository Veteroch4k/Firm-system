package com.veteroch4k.factory_service.services;

import com.veteroch4k.factory_service.repository.FactoryRepository;
import org.springframework.stereotype.Service;

@Service
public class MaterialsService {

  OrderServiceClient client;

  public MaterialsService(OrderServiceClient client) {
    this.client = client;
  }

  public Long getOrderMaterials(long orderId) {

    return client.getOrderOperationId(orderId);

  }

}
