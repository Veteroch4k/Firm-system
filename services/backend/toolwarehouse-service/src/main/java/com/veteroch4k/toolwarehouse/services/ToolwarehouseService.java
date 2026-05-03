package com.veteroch4k.toolwarehouse.services;

import com.veteroch4k.toolwarehouse.models.FactoryTools;
import com.veteroch4k.toolwarehouse.models.MovementType;
import com.veteroch4k.toolwarehouse.models.ToolAccounting;
import com.veteroch4k.toolwarehouse.repositories.FactoryToolsRepository;
import com.veteroch4k.toolwarehouse.repositories.ToolAccountingRepository;
import com.veteroch4k.toolwarehouse.repositories.ToolTypeRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToolwarehouseService {

  private final FactoryToolsRepository factoryToolsRepository;
  private final ToolAccountingRepository toolAccountingRepository;
  private final ToolTypeRepository toolTypeRepository;

  public List<FactoryTools> getFactoryTools(int factoryId) {

    return factoryToolsRepository.findAllByFactoryId(factoryId);

  }

  public void supplyTool(int toolTypeId, int quantity, int factoryId) {
    ToolAccounting accounting = new ToolAccounting();
    accounting.setToolType(toolTypeRepository.getReferenceById(toolTypeId));
    accounting.setQuantity(quantity);
    accounting.setType(MovementType.INCOME);
    accounting.setFactoryId(factoryId);
    accounting.setEmployeeId(0);
    accounting.setDate(LocalDate.now());

    toolAccountingRepository.save(accounting);
  }

  public void spendToolsForOrder(int toolTypeId, int quantity, int factoryId) {
    ToolAccounting accounting = new ToolAccounting();
    accounting.setToolType(toolTypeRepository.getReferenceById(toolTypeId));
    accounting.setQuantity(quantity);
    accounting.setType(MovementType.OUTCOME);
    accounting.setFactoryId(factoryId);
    accounting.setEmployeeId(0);
    accounting.setDate(LocalDate.now());

    toolAccountingRepository.save(accounting);

  }



}
