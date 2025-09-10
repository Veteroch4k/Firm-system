package com.veteroch4k.order.repository;

import com.veteroch4k.order.model.Order;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  Order getOrderById(long id);

  List<Order> findAllByOrder_dateBetween(LocalDate start, LocalDate finish);



}
