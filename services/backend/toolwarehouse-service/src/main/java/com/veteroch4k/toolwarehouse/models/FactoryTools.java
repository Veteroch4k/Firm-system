package com.veteroch4k.toolwarehouse.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.service.annotation.GetExchange;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "factory_tools")
public class FactoryTools {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int toolTyper_id;

  private int quantity;

}
