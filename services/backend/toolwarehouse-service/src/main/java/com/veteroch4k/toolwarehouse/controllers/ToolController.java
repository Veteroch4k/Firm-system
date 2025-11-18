package com.veteroch4k.toolwarehouse.controllers;

import com.veteroch4k.toolwarehouse.models.Tool;
import com.veteroch4k.toolwarehouse.repositories.ToolRepository;
import java.util.List;
import java.util.Optional;
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

@RestController
@RequestMapping("/api/tool")
public class ToolController {

  private final ToolRepository toolRepository;

  public ToolController(ToolRepository toolRepository) {
    this.toolRepository = toolRepository;
  }

  @GetMapping("/all")
  public Page<Tool> getTools(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  )
  {
    return toolRepository.findAll(PageRequest.of(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Tool> getToolById(@PathVariable long id) {

    return toolRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());

  }

  @GetMapping("/by-type")
  public ResponseEntity<List<Tool>> getToolsByType(
      @RequestParam String typeName
  )
  {
    return ResponseEntity.ok(toolRepository.findToolsByToolType_Name(typeName.trim()));
  }

  @PostMapping("/create-tool")
  @ResponseStatus(HttpStatus.CREATED)
  public Tool createTool(@RequestBody Tool tool) {

    return toolRepository.save(tool);

  }

  @PutMapping("/{id}")
  public ResponseEntity<Tool> updateTool(
      @PathVariable long id,
      @RequestBody Tool tool)
  {

    Optional<Tool> optionalTool = toolRepository.findById(id);

    if (optionalTool.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Tool updatedTool = optionalTool.get();

    // Явно копируем только нужные поля
    updatedTool.setToolType(tool.getToolType());


    return ResponseEntity.ok(toolRepository.save(updatedTool));

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTool(@PathVariable long id) {
    if (toolRepository.existsById(id)) {
      toolRepository.deleteById(id);
      return ResponseEntity.noContent().build(); // 204
    }
    return ResponseEntity.notFound().build(); // 404

  }

}
