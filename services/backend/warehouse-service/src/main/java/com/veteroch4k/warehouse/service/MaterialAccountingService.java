package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.models.MaterialAccounting;
import com.veteroch4k.warehouse.models.MovementType;
import com.veteroch4k.warehouse.repositories.MaterialAccountingRepository;
import com.veteroch4k.warehouse.repositories.MaterialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialAccountingService {

    private final MaterialAccountingRepository materialAccountingRepository;
    private final MaterialRepository materialRepository;


    /*
        По факту тут пока можно беез аннотации @Transactional, но оставлю на будущее, чтоб потом форс-мажоров не было
     */

    @Transactional
    public void supplyMaterial(Long materialId, Long quantity, Long factoryId) {

        log.debug("Начало создания записи о поставке материалов в фабрику ID: {}.", factoryId);

        MaterialAccounting accounting = new MaterialAccounting();
        accounting.setMaterial(materialRepository.getReferenceById(materialId));
        accounting.setQuantity(quantity);
        accounting.setType(MovementType.INCOME);
        accounting.setFactoryId(factoryId);
        accounting.setEmployerId(1L);
        accounting.setDate(LocalDate.now());

        materialAccountingRepository.save(accounting);

        log.debug("записи о поставке материалов в фабрику ID: {} успешно создана", factoryId);


    }

    @Transactional
    public void spendMaterialForOrder(Long materialId, Long quantity, Long factoryId) {

        log.debug("Резервация материалов фабрики ID: {} для заказа", factoryId);


        MaterialAccounting accounting = new MaterialAccounting();
        accounting.setMaterial(materialRepository.getReferenceById(materialId));
        accounting.setQuantity(quantity);
        accounting.setType(MovementType.OUTCOME);
        accounting.setFactoryId(factoryId);
        accounting.setEmployerId(1L);
        accounting.setDate(LocalDate.now());

        materialAccountingRepository.save(accounting);

        log.debug("Резервация материалов фабрики ID: {} для заказа прошла успешно", factoryId);

    }



}
