package com.veteroch4k.toolwarehouse.controllers;

import com.veteroch4k.toolwarehouse.dto.ToolRequest;
import com.veteroch4k.toolwarehouse.dto.ToolResponse;
import com.veteroch4k.toolwarehouse.services.ToolService;
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
@RequestMapping("/api/tool")
public class ToolController {

    private final ToolService toolService;

    @GetMapping("/all")
    public Page<ToolResponse> getTools(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return toolService.findAllTools(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public ToolResponse getToolById(@PathVariable Long id) {

        return toolService.findToolById(id);

    }

    @GetMapping("/by-type")
    public Page<ToolResponse> getToolsByType(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam String typeName
    ) {
        return toolService.findToolsByToolTypeName(typeName.trim(), PageRequest.of(page, size));
    }

    @PostMapping("/create-tool")
    @ResponseStatus(HttpStatus.CREATED)
    public ToolResponse createTool(@RequestBody ToolRequest toolRequest) {

        return toolService.saveTool(toolRequest);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTool(
            @PathVariable Long id,
            @RequestBody ToolRequest toolRequest) {
        toolService.updateTool(id, toolRequest);

    }

    @DeleteMapping("/{id}")
    public void deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);

    }

}
