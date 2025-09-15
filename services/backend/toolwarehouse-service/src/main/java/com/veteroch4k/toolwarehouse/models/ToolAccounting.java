package com.veteroch4k.toolwarehouse.models;

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
@Table(name = "tools_accounting")
public class ToolAccounting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //private ArrayList<Long> tools_id = new ArrayList<>();

  private int toolType_id;

  private int factory_id;

  private int order_id;

}
