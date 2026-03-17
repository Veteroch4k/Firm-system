package com.veteroch4k.order.service;

import com.veteroch4k.order.model.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final KafkaTemplate<String, String> kafkaTemplate;

  // Название топика
  private static final String TOPIC = "order-events";

  public void createOrder() {

    // пока как заглушка для теста кафки

    OrderCreatedEvent event = new OrderCreatedEvent(1488L, 1488L, 10);

    kafkaTemplate.send(TOPIC, String.valueOf(event.orderId()), "Хуй");


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
