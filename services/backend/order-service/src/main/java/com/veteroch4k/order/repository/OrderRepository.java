package com.veteroch4k.order.repository;

import com.veteroch4k.order.model.Order;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

  Page<Order> findByOrderDate(LocalDate date, Pageable pageable);

  @Query(value = "SELECT * FROM order_service.orders o WHERE order_date >= :start AND order_date <= :finish", nativeQuery = true)
  Page<Order> findByOrderDateBetween(LocalDate start, LocalDate finish, Pageable pageable);

}
