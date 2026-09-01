package com.veteroch4k.warehouse.controllers;

import com.veteroch4k.warehouse.dto.MaterialRequest;
import com.veteroch4k.warehouse.dto.MaterialResponse;
import com.veteroch4k.warehouse.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/material")
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping("/all")
    public Page<MaterialResponse> getMaterials(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return materialService.findAllMaterials(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public MaterialResponse getMaterial(@PathVariable Long id) {

        return materialService.findMaterialById(id);

    }

    @PostMapping("/create-material")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMaterial(@RequestBody MaterialRequest materialRequest) {
        materialService.saveMaterial(materialRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateMaterial(
            @PathVariable Long id,
            @RequestBody MaterialRequest materialRequest) {
        materialService.updateMaterialById(id, materialRequest);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterialById(id);
    }

}
