package com.veteroch4k.toolwarehouse.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "free_tools")
public class FreeTools {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int tool_id;

  private int toolType_id;

  private LocalDate receiveDate;


}
