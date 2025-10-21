package com.veteroch4k.toolwarehouse.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "toolType_id")
  private ToolType toolType;

  @Column(nullable = false)
  private int factory_id;

  @Column(nullable = false)
  private int order_id;

}
