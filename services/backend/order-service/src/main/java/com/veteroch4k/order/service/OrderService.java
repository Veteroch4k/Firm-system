package com.veteroch4k.order.service;

import com.veteroch4k.order.model.Order;
import com.veteroch4k.order.model.OrderCreatedEvent;
import com.veteroch4k.order.repository.OrderRepository;
import java.time.LocalDate;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final KafkaProducerService kafkaProducerService;
  private final OrderRepository repository;

  public void createOrder(Order order) {

    order.setOrderDate(LocalDate.now());
    order.setFinishDate(LocalDate.now().plusDays(10));
    repository.save(order);

    OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getProductId(), order.getProductQuantity());

    kafkaProducerService.sendOrderCreatedEvent(event);


  }

//  public LocalDate getOrderDeadLine(Order order) throws IOException {
//
//    LocalDate finish = order.getFinish_date(); // Вначале дата начала и конца совпадают
//
//    int hours = 0;
//    int drawing_id = repository.getDrawingByProductId(order.getProduct_id()).getId();
//
//    int op_id = repository.getAllDrawings()
//        .stream().filter(x->x.getId() == drawing_id).findFirst().get().getOperation_id();
//    hours = repository.getAllOperations()
//        .stream().filter(x->x.getId() == op_id).findFirst().get().getDuration();
//
//    hours *= order.getProduct_quantity();
//
//    int days = hours / 24;
//
//    finish = finish.plusDays(days);
//
//    return finish;
//
//  }

}
