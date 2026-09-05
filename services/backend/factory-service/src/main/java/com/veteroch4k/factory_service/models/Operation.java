package com.veteroch4k.factory_service.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "operations")
public class Operation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Long duration;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "factory_id")
  private Factory factory;


}
