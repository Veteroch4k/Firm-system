package com.veteroch4k.warehouse.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "factory_materials")
public class FactoryMaterials {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;


  private int factory_id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "material_id")
  private Material material;

  private int quantity;

  private LocalDate createdAt;


}
