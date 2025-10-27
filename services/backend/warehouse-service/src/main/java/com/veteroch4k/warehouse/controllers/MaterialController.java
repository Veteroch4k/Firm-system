package com.veteroch4k.warehouse.controllers;

import com.veteroch4k.warehouse.models.Material;
import com.veteroch4k.warehouse.repositories.MaterialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.HandlerMapping;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

  private final MaterialRepository materialRepository;
  private final HandlerMapping resourceHandlerMapping;

  public MaterialController(MaterialRepository materialRepository,
      HandlerMapping resourceHandlerMapping) {
    this.materialRepository = materialRepository;
    this.resourceHandlerMapping = resourceHandlerMapping;
  }

  @GetMapping("/all")
  public Page<Material> getMaterials(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size )
  {
    return materialRepository.findAll(PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Material> getMaterial(@PathVariable long id) {

    return materialRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());

  }

  @PostMapping("/create-material")
  @ResponseStatus(HttpStatus.CREATED)
  public Material createMaterial(@RequestBody Material material) {
    return materialRepository.save(material);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Material> updateMaterial(
      @PathVariable long id,
      @RequestBody Material material)
  {
    if(!materialRepository.existsById(id)) return ResponseEntity.notFound().build();

    material.setId(id);

    Material updated = materialRepository.save(material);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/id")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteMaterial(@PathVariable long id) {
    materialRepository.deleteById(id);
  }

}
