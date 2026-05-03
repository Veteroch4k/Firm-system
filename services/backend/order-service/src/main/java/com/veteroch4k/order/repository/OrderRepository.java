package com.veteroch4k.order.repository;

import com.veteroch4k.order.model.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

  List<Order> findByOrderDate(LocalDate date);

  List<Order> findByOrderDateBetween(LocalDate start, LocalDate finish);

  Optional<Order> findById(Integer id);

}
