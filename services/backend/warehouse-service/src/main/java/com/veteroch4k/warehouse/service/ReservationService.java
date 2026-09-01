package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.models.FactoryMaterials;
import com.veteroch4k.warehouse.models.commands.MaterialReservationCommand;
import com.veteroch4k.warehouse.models.commands.RequiredMaterial;
import com.veteroch4k.warehouse.repositories.FactoryMaterialsRepository;
import com.veteroch4k.warehouse.repositories.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final FactoryMaterialService factoryMaterialService;
  private final MaterialAccountingService accountingService;

  @Transactional
  public void processReservation(MaterialReservationCommand command) {

    List<FactoryMaterials> materials = factoryMaterialService.getFactoryMaterials(command.factoryId());

    Map<Long, Long> currentBalances = new HashMap<>();

    for (FactoryMaterials material : materials) {
      currentBalances.put(material.getMaterial().getId(), material.getQuantity());
    }

    for (RequiredMaterial requiredMaterial : command.materials()) {
      long currentAmount = currentBalances.getOrDefault(requiredMaterial.materialId(), 0L);

      if (currentAmount < requiredMaterial.quantity()) {
        accountingService.supplyMaterial(requiredMaterial.materialId(), requiredMaterial.quantity() - currentAmount, command.factoryId());
      }

      accountingService.spendMaterialForOrder(requiredMaterial.materialId(), requiredMaterial.quantity(), command.factoryId());
    }


  }




}
