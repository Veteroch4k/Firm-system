package com.veteroch4k.order.controller;

import com.veteroch4k.order.dto.orderDTO.OrderRequestDTO;
import com.veteroch4k.order.dto.orderDTO.OrderResponseDTO;
import com.veteroch4k.order.service.OrderService;
import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {


  private final OrderService service;

  @GetMapping("/all")
  public Page<OrderResponseDTO> orders(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size)
  {
    return service.findPageOrders(PageRequest.of(page, size));

  }


  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public void createOrder(@RequestBody OrderRequestDTO orderReqest) {

    log.info("Получен запрос на создание заказа. Product ID: {}, Quantity: {}", orderReqest.productId(), orderReqest.productQuantity());

    service.createOrder(orderReqest);

  }

  @GetMapping("/by-date")
  public Page<OrderResponseDTO> getOrdersByDate(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  )

  {

    return service.findOrdersByOrderingDate(date, PageRequest.of(page, size));
  }

  @GetMapping("/between-dates")
  public Page<OrderResponseDTO> getOrdersByDateRange(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) {
      log.debug("Поиск заказов между датами {} и {}. Страница: {}, Размер: {}", start, end, page, size);

      return service.findByOrdersByDateBetween(start, end, PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public OrderResponseDTO getOrderById(@PathVariable Integer id) {
    return service.findOrderById(id);
  }


}


