package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.models.FactoryMaterials;
import com.veteroch4k.warehouse.repositories.FactoryMaterialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FactoryMaterialService {

    private final FactoryMaterialsRepository factoryMaterialsRepository;


    public List<FactoryMaterials> getFactoryMaterials(Long id) {
        return factoryMaterialsRepository.findAllByFactoryId(id);
    }

}
