package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.models.FactoryMaterials;
import com.veteroch4k.warehouse.models.Material;
import com.veteroch4k.warehouse.models.MaterialAccounting;
import com.veteroch4k.warehouse.models.MovementType;
import com.veteroch4k.warehouse.repositories.FactoryMaterialsRepository;
import com.veteroch4k.warehouse.repositories.MaterialAccountingRepository;
import com.veteroch4k.warehouse.repositories.MaterialRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WarehouseService {

  private final FactoryMaterialsRepository factoryMaterialsRepository;
  private final MaterialAccountingRepository materialAccountingRepository;
  private final MaterialRepository materialRepository;

  public List<FactoryMaterials> getFactoryMaterials(int id) {
    return factoryMaterialsRepository.findAllByFactoryId(id);
  }

  public void supplyMaterial(int materialId, int quantity, int factoryId) {

    MaterialAccounting accounting = new MaterialAccounting();
    accounting.setMaterial(materialRepository.getReferenceById(materialId));
    accounting.setQuantity(quantity);
    accounting.setType(MovementType.INCOME);
    accounting.setFactoryId(factoryId);
    accounting.setEmployerId(0);
    accounting.setDate(LocalDate.now());

    materialAccountingRepository.save(accounting);

  }

  public void spendMaterialForOrder(int materialId, int quantity, int factoryId) {
    MaterialAccounting accounting = new MaterialAccounting();
    accounting.setMaterial(materialRepository.getReferenceById(materialId));
    accounting.setQuantity(quantity);
    accounting.setType(MovementType.OUTCOME);
    accounting.setFactoryId(factoryId);
    accounting.setEmployerId(0);
    accounting.setDate(LocalDate.now());

    materialAccountingRepository.save(accounting);
  }

}
