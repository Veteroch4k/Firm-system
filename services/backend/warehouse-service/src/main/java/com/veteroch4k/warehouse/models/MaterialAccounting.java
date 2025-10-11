package com.veteroch4k.warehouse.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.springframework.util.Assert;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "material_accounting")
public class MaterialAccounting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "material_id")
  private Material material;

  private int quantity;

  @Enumerated(EnumType.STRING)
  private MovementType type;

  @Column(name = "factory_id", nullable = false)
  private Long factoryId;


  @Column(name = "employer_id", nullable = false)
  private Long employerId;


  private LocalDate date;

}

enum MovementType {
  INCOME,    // Приход на склад
  OUTCOME,   // Расход со склада
  TRANSFER   // Перемещение между складами
}
