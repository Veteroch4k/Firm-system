package com.veteroch4k.toolwarehouse.service;

import com.veteroch4k.toolwarehouse.dto.ToolRequest;
import com.veteroch4k.toolwarehouse.exceptions.ResourceNotFoundException;
import com.veteroch4k.toolwarehouse.mappers.ToolMapper;
import com.veteroch4k.toolwarehouse.models.Tool;
import com.veteroch4k.toolwarehouse.repositories.ToolRepository;
import com.veteroch4k.toolwarehouse.repositories.ToolTypeRepository;
import com.veteroch4k.toolwarehouse.services.ToolService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToolServiceTest {

    @InjectMocks
    private ToolService toolService;

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private ToolTypeRepository typeRepository;

    @Mock
    private ToolMapper toolMapper;


    @Test
    void shouldThrowResourceNotFoundWhenFindToolById() {

        Long id = 1L;

        when(toolRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->  toolService.findToolById(id));


    }

    @Test
    void shouldThrowResourceNotFoundWhenSaveTool() {

        ToolRequest request = new ToolRequest(1L);

        when(typeRepository.findById(request.toolTypeId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->  toolService.saveTool(request));


    }

    @Test
    void shouldThrowResourceNotFoundToolWhenUpdateTool() {

        ToolRequest request = new ToolRequest(1L);
        Long id = 5L;

        when(toolRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->  toolService.updateTool(id, request));


    }

    @Test
    void shouldThrowResourceNotFoundToolTypeWhenUpdateTool() {

        ToolRequest request = new ToolRequest(1L);
        Long id = 5L;

        when(toolRepository.findById(id)).thenReturn(Optional.of(new Tool()));

        when(typeRepository.existsById(request.toolTypeId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, ()->  toolService.updateTool(id, request));


    }

    @Test
    void shouldThrowResourceNotFoundToolWhenDeleteTool() {

        Long id = 5L;

        when(toolRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->  toolService.deleteTool(id));


    }
}
