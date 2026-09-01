package com.veteroch4k.warehouse.service;

import com.veteroch4k.warehouse.dto.MaterialRequest;
import com.veteroch4k.warehouse.dto.MaterialResponse;
import com.veteroch4k.warehouse.exceptions.ResourceNotFoundException;
import com.veteroch4k.warehouse.models.Material;
import com.veteroch4k.warehouse.repositories.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MaterialService {


    private final MaterialRepository materialRepository;


    public Page<MaterialResponse> findAllMaterials(PageRequest of) {

        Page<Material> materials = materialRepository.findAll(of);

        return materials.map(this::materialToResponse);

    }

    public MaterialResponse findMaterialById(Long id) {

        Material material = materialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Материал с ID: " + id + " не найден."));

        return materialToResponse(material);

    }

    @Transactional
    public void updateMaterialById(Long id, MaterialRequest materialRequest) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Материал с ID: " + id + " не найден."));

        material.setName(materialRequest.name());


    }

    public void saveMaterial(MaterialRequest materialRequest) {

        Material material = new Material();
        material.setName(materialRequest.name());

        materialRepository.save(material);

    }

    public void deleteMaterialById(Long id) {

        if (!materialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Материал с ID: " + id + " не найден.");
        }

    }



    private MaterialResponse materialToResponse(Material material) {

        return new MaterialResponse(
                material.getId(),
                material.getName()
        );


    }

}
