package com.veteroch4k.order.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_accounting")
public class OrderAccounting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int factory_id;

  private int product_id;

  private int quantity;

}
