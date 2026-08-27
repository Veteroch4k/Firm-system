package com.veteroch4k.product.controllers;

import com.veteroch4k.product.dto.drawing.DrawingResponse;
import com.veteroch4k.product.models.Drawing;
import com.veteroch4k.product.repositories.DrawingRepository;
import java.util.List;

import com.veteroch4k.product.services.DrawingService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/drawing")
public class DrawingController {

  private final DrawingService drawingService;

  @GetMapping("/all")
  public Page<DrawingResponse> getDrawings(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) {
    return drawingService.findAllDrawings(PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public DrawingResponse getDrawing(@PathVariable Long id) {

    return drawingService
        .findDrawingById(id);
  }

}
