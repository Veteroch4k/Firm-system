package com.veteroch4k.factory_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operation_tools")
public class OperationTools {

  @EmbeddedId
  private OperationToolsId operationToolsId;

  @Column(name = "quantity")
  private Long quantity;

}
