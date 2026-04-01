package com.veteroch4k.factory_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class OperationToolsId {

  @Column(name = "operation_id")
  private int operation_id;

  @Column(name = "toolType_id")
  private int toolType_id;

}
