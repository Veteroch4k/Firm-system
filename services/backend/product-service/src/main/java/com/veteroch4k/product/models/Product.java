package com.veteroch4k.product.models;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "description")
  private String description;


  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "drawing_id")
  private Drawing drawing;

}
