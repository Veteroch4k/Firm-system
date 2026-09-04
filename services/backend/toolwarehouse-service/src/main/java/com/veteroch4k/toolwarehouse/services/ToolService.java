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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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

        Tool tool = toolRepository.findById(id).orElseThrow(() -> {
                    log.warn("Инструмент с ID: {} не найден при запросе по ID", id);
                    return new ResourceNotFoundException("Инструмент с ID: " + id + " не найден.");
        });

        return toolToToolResponse(tool);
    }

    public Page<ToolResponse> findToolsByToolTypeName(String name, Pageable of) {

        Page<Tool> tools = toolRepository.findAllByToolType_Name(name, of);

        return tools.map(this::toolToToolResponse);
    }

    @Transactional
    public ToolResponse saveTool(ToolRequest toolRequest) {

        ToolType toolType = toolTypeRepository.findById(toolRequest.toolTypeId())
                .orElseThrow(() -> {
                    log.warn("Тип_Инструмента с ID: {} не найден при запросе на создание инструмента", toolRequest.toolTypeId());
                    return new ResourceNotFoundException("Тип инструмента не найден c ID: " + toolRequest.toolTypeId());

                });

        Tool tool = new Tool();
        tool.setToolType(toolType);

        return toolToToolResponse(toolRepository.save(tool));

    }

    @Transactional
    public void updateTool(Long id, ToolRequest toolRequest) {

        Tool tool = toolRepository.findById(id).orElseThrow(() -> {
            log.warn("Инструмент с ID: {} не найден при запросе на обновление", id);
            return new ResourceNotFoundException("Инструмент с ID: " + id + " не найден.");
        });

        if (!toolTypeRepository.existsById(toolRequest.toolTypeId())) {
            log.warn("Тип_Инструмента с ID: {} не найден при запросе на обновление инструмента", toolRequest.toolTypeId());
            throw new ResourceNotFoundException("Тип инструмента не найден c ID: " + toolRequest.toolTypeId());
        }

        tool.setToolType(toolTypeRepository.getReferenceById(toolRequest.toolTypeId()));

    }

    @Transactional
    public void deleteTool(Long id) {

        log.info("Запрос на удаление инструмента с ID: {}", id);

        Tool tool = toolRepository.findById(id).orElseThrow(() -> {
            log.warn("Инструмент с ID: {} не найден при запросе на удаление", id);
            return new ResourceNotFoundException("Инструмент с ID: " + id + " не найден.");

        });

        toolRepository.delete(tool);

        log.info("Инструмент с ID: {} успешно удалён", id);


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
