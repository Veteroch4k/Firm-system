package com.veteroch4k.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "product_quantity")
  private Long productQuantity;

  @Column(name = "order_date")
  private LocalDate orderDate;

  @Column(name = "finish_date")
  private LocalDate finishDate;

}
