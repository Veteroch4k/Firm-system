package com.veteroch4k.warehouse.models;


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
@Table(name = "op_materials")
public class OpMaterials {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int operation_id;

  private int material_id;

  private int quantity;

}
