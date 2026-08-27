package com.veteroch4k.product.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "drawing")
public class Drawing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "op_id")
  private Long operationId;
  // Операции

  @Column(name = "factory_id")
  private Long factoryId;
  // Цех

}
