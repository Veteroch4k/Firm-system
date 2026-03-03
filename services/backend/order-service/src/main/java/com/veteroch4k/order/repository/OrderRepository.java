package com.veteroch4k.order.repository;

import com.veteroch4k.order.model.Orders;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

  List<Orders> findByOrderDate(LocalDate date);

  List<Orders> findByOrderDateBetween(LocalDate start, LocalDate finish);

  Optional<Orders> findById(Long id);

}
