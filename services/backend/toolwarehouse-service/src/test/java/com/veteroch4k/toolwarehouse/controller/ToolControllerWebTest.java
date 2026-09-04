package com.veteroch4k.toolwarehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veteroch4k.toolwarehouse.controllers.ToolController;
import com.veteroch4k.toolwarehouse.dto.ToolRequest;
import com.veteroch4k.toolwarehouse.exceptions.ResourceNotFoundException;
import com.veteroch4k.toolwarehouse.services.ToolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ToolController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ToolControllerWebTest {

    @MockitoBean
    private ToolService toolService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldReturn400WhenBadParamGetTools() throws Exception {

        String invalidPage = "-1";

        mockMvc.perform(
                get("/api/tool/all")
                        .param("page", invalidPage)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn400WhenInvalidIdGetToolById() throws Exception {

        Long invalidId = -1L;

        mockMvc.perform(
                get("/api/tool/{id}", invalidId)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn404WhenNoFoundGetToolById() throws Exception {

        Long id = 10L;

        when(toolService.findToolById(id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/tool/{id}", id)
        ).andExpect(
                status().isNotFound()
        );

    }

    @Test
    void shouldReturn400WhenInvalidNameGetToolsByTypeName() throws Exception {

        String invalidTypeName = "";

        mockMvc.perform(
                get("/api/tool/by-type-name")
                        .param("typeName", invalidTypeName)
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    void shouldReturn400WhenInvalidRequestCreateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(-10L);

        mockMvc.perform(
                post("/api/tool")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn404WhenNoFoundCreateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(10L);

        when(toolService.saveTool(toolRequest)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                post("/api/tool")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isNotFound()
        );

    }

    @Test
    void shouldReturn400WhenInvalidIdUpdateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(10L);
        Long invalidId = -1L;

        mockMvc.perform(
                put("/api/tool/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn400WhenInvalidRequestUpdateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(-10L);
        Long id = 1L;

        mockMvc.perform(
                put("/api/tool/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn404WhenNoFoundUpdateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(10L);
        Long id = 1L;

        doThrow(new ResourceNotFoundException(""))
                .when(toolService).updateTool(id, toolRequest);

        mockMvc.perform(
                put("/api/tool/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isNotFound()
        );


    }

    @Test
    void shouldReturn400WhenInvalidIdDeleteTool() throws Exception {

        Long invalidId = -1L;

        mockMvc.perform(
                delete("/api/tool/{id}", invalidId)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn404WhenNoFoundDeleteTool() throws Exception {

        Long id = 10L;

        doThrow(new ResourceNotFoundException(""))
                .when(toolService).deleteTool(id);

        mockMvc.perform(
                delete("/api/tool/{id}", id)
        ).andExpect(
                status().isNotFound()
        );

    }


}
