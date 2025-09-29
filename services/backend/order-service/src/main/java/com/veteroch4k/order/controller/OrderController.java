package com.veteroch4k.order.controller;

import com.veteroch4k.order.dto.DateRange;
import com.veteroch4k.order.model.Order;
import com.veteroch4k.order.model.OrderAccounting;
import com.veteroch4k.order.repository.OrderAccountingRepository;
import com.veteroch4k.order.repository.OrderRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

  private OrderRepository orderRepository;
  private OrderAccountingRepository accountingRepository;


  @GetMapping
  public ResponseEntity<List<Order>> orders() {

    return ResponseEntity.ok(orderRepository.findAll());

  }

  @GetMapping("/accounting")
  public ResponseEntity<List<OrderAccounting>> orderAccounting() {

    return ResponseEntity.ok(accountingRepository.findAll());

  }

  @PostMapping("/create-order")
  @ResponseStatus(HttpStatus.CREATED)
  public void createOrder(@RequestBody Order order) {

    // По идее должны сетать дедлайн по бизнес-логике

    orderRepository.save(order);


  }

  @GetMapping("/by-date")
  public ResponseEntity<List<Order>> getOrdersByDateRange(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

      List<Order> orders = new ArrayList<>();
      //= orderRepository.findAllByOrder_dateBetween(start, end);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    return orderRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

}


