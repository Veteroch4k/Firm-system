package com.veteroch4k.toolwarehouse.services;

import com.veteroch4k.toolwarehouse.dto.ToolRequest;
import com.veteroch4k.toolwarehouse.dto.ToolResponse;
import com.veteroch4k.toolwarehouse.dto.ToolTypeResponse;
import com.veteroch4k.toolwarehouse.exceptions.ResourceNotFoundException;
import com.veteroch4k.toolwarehouse.models.Tool;
import com.veteroch4k.toolwarehouse.models.ToolType;
import com.veteroch4k.toolwarehouse.repositories.ToolRepository;
import com.veteroch4k.toolwarehouse.repositories.ToolTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository toolRepository;
    private final ToolTypeRepository toolTypeRepository;


    public Page<ToolResponse> findAllTools(PageRequest of) {

        Page<Tool> tools = toolRepository.findAll(of);

        return tools.map(this::toolToToolResponse);

    }

    public ToolResponse findToolById(Long id) {

        Tool tool = toolRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Инструмент с ID: " + id + " не найден.")
        );

        return toolToToolResponse(tool);
    }

    public Page<ToolResponse> findToolsByToolTypeName(String name, Pageable of) {

        Page<Tool> tools = toolRepository.findAllByToolType_Name(name, of);

        return tools.map(this::toolToToolResponse);
    }

    @Transactional
    public ToolResponse saveTool(ToolRequest toolRequest) {

        ToolType toolType = toolTypeRepository.findById(toolRequest.toolTypeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Тип инструмента не найден c ID: " + toolRequest.toolTypeId())
                );

        Tool tool = new Tool();
        tool.setToolType(toolType);

        return toolToToolResponse(toolRepository.save(tool));

    }

    @Transactional
    public void updateTool(Long id, ToolRequest toolRequest) {

        Tool tool = toolRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Инструмент с ID: " + id + " не найден.")
        );

        if(!toolTypeRepository.existsById(toolRequest.toolTypeId())) {
            throw new ResourceNotFoundException("Тип инструмента не найден c ID: " + toolRequest.toolTypeId());
        }

        tool.setToolType(toolTypeRepository.getReferenceById(toolRequest.toolTypeId()));

    }

    @Transactional
    public void deleteTool(Long id) {

        Tool tool = toolRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Инструмент с ID: " + id + " не найден.")
        );

        toolRepository.delete(tool);

    }

    private ToolResponse toolToToolResponse(Tool tool) {

        return new ToolResponse(
                tool.getId(),
                new ToolTypeResponse(
                        tool.getToolType().getId(),
                        tool.getToolType().getName(),
                        tool.getToolType().getDescription()
                )
        );

    }
}
