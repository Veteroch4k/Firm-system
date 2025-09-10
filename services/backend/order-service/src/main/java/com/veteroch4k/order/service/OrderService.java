package com.veteroch4k.order.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

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
