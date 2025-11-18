package com.veteroch4k.order.controller;

import com.veteroch4k.order.dto.DateRange;
import com.veteroch4k.order.model.Order;
import com.veteroch4k.order.model.OrderAccounting;
import com.veteroch4k.order.repository.OrderAccountingRepository;
import com.veteroch4k.order.repository.OrderRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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

  private final OrderRepository orderRepository;
  private final OrderAccountingRepository accountingRepository;

  public OrderController(OrderRepository orderRepository,
      OrderAccountingRepository accountingRepository) {
    this.orderRepository = orderRepository;
    this.accountingRepository = accountingRepository;
  }

  @GetMapping("/all")
  public Page<Order> orders(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size)
  {

    return orderRepository.findAll(PageRequest.of(page, size));

  }

  @GetMapping("/accounting")
  public Page<OrderAccounting> orderAccounting(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) {

    return accountingRepository.findAll(PageRequest.of(page, size));

  }

  @PostMapping("/create-order")
  @ResponseStatus(HttpStatus.CREATED)
  public Order createOrder(@RequestBody Order order) {

    return orderRepository.save(order);


  }

  @GetMapping("/by-date")
  public ResponseEntity<List<Order>> getOrdersByDate(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
  {
    return ResponseEntity.ok(orderRepository.findByOrderDate(date));
  }

  @GetMapping("/between-dates")
  public ResponseEntity<List<Order>> getOrdersByDateRange(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

      List<Order> orders = orderRepository.findByOrderDateBetween(start, end);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    return orderRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }



}


