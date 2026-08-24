package com.veteroch4k.order.repository;

import com.veteroch4k.order.model.Order;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

  Page<Order> findByOrderDate(LocalDate date, Pageable pageable);

  Page<Order> findByOrderDateBetween(LocalDate start, LocalDate finish, Pageable pageable);

}
