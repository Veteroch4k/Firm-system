package com.veteroch4k.factory_service.model;


import jakarta.persistence.*;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "factories")
public class Factory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "factory",fetch = FetchType.LAZY)
  private List<Operation> operations;


}
