package com.veteroch4k.order.service;

import com.veteroch4k.order.dto.orderDTO.OrderRequestDTO;
import com.veteroch4k.order.dto.orderDTO.OrderResponseDTO;
import com.veteroch4k.order.exceptions.ResourceNotFoundException;
import com.veteroch4k.order.model.Order;
import com.veteroch4k.order.model.OrderCreatedEvent;
import com.veteroch4k.order.repository.OrderRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final KafkaProducerService kafkaProducerService;
  private final OrderRepository orderRepository;

  public Page<OrderResponseDTO> findPageOrders(PageRequest of) {

    Page<Order> orders = orderRepository.findAll(of);

    return orders.map(OrderResponseDTO::new);
  }


  public Page<OrderResponseDTO> findOrdersByOrderingDate(LocalDate date, PageRequest of) {

    Page<Order> orders = orderRepository.findByOrderDate(date, of);
    return orders.map(OrderResponseDTO::new);
  }

  public Page<OrderResponseDTO> findByOrdersByDateBetween(LocalDate start, LocalDate end, PageRequest of) {

    if(start.isAfter(end)) {
      throw new IllegalArgumentException("Дата начала не может быть позже даты окончания");
    }

    Page<Order> orders = orderRepository.findByOrderDateBetween(start, end, of);

    return orders.map(OrderResponseDTO::new);

  }

  public OrderResponseDTO findOrderById(Long id) {

    Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Заказ с ID " + id + " не найден"));

    return new OrderResponseDTO(order);
  }

  public void createOrder(OrderRequestDTO orderRequest) {

    log.info("Начало создания заказа для продукта ID: {}", orderRequest.productId());

    Order order = new Order();
    order.setProductId(orderRequest.productId());
    order.setProductQuantity(orderRequest.productQuantity());

    order.setOrderDate(LocalDate.now());
    order.setFinishDate(LocalDate.now().plusDays(10));
    orderRepository.save(order);

    log.info("Заказ успешно сохранен в БД. ID: {}", order.getId());

    OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getProductId(), order.getProductQuantity());

    kafkaProducerService.sendOrderCreatedEvent(event);

    log.debug("Событие OrderCreatedEvent отправлено в Kafka: {}", event);


  }

  public void deleteAllOrders() {
    orderRepository.deleteAll();
  }
}
