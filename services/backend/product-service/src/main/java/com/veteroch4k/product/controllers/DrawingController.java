package com.veteroch4k.product.controllers;

import com.veteroch4k.product.models.Drawing;
import com.veteroch4k.product.repositories.DrawingRepository;
import java.util.List;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/drawing")
public class DrawingController {

  private final DrawingRepository drawingRepository;

  public DrawingController(DrawingRepository drawingRepository) {
    this.drawingRepository = drawingRepository;
  }

  @GetMapping("/all")
  public Page<Drawing> getDrawings(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) {
    return drawingRepository.findAll(PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Drawing> getDrawing(@PathVariable long id) {

    return drawingRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

}
