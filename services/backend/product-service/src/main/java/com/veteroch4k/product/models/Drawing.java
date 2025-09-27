package com.veteroch4k.product.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drawing")
public class Drawing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;


  private int op_id;
  // Операции

  private int factory_id;
  // Цех

}
