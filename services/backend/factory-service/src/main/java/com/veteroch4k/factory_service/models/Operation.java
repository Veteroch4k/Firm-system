package com.veteroch4k.factory_service.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mapping.IdentifierAccessor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "operations")
public class Operation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  private int duration;

  private int factory_id;


}
