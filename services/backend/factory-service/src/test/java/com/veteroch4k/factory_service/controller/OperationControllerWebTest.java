package com.veteroch4k.factory_service.controller;

import com.veteroch4k.factory_service.dto.operation.OperationRequest;
import com.veteroch4k.factory_service.exceptions.ResourceNotFoundException;
import com.veteroch4k.factory_service.services.OperationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OperationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OperationControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OperationService operationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn400WhenBadParamGetOperations() throws Exception {

        String invalidSize = "105";

        mockMvc.perform(
                get("/api/operation/all")
                        .param("size", invalidSize)
        ).andExpect(
                status().isBadRequest()
        );



    }


    @Test
    void shouldReturn400WhenInvalidIdGetOperation() throws Exception {

        Long invalidId = -1L;

        mockMvc.perform(
                get("/api/operation/{id}", invalidId)
        ).andExpect(
                status().isBadRequest()
        );



    }

    @Test
    void shouldReturn404WhenNotFoundGetOperation() throws Exception {

        Long id = 1L;

        when(operationService.findOperationById(id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/operation/{id}", id)
        ).andExpect(
                status().isNotFound()
        );



    }

    @Test
    void shouldReturn400WhenInvalidDataCreateOperation() throws Exception {

        String blankName = "";

        OperationRequest request = new OperationRequest(
                blankName, 1L, 1L
        );


        mockMvc.perform(
                post("/api/operation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        );



    }

    @Test
    void shouldReturn404WhenNotFoundCreateOperation() throws Exception {


        OperationRequest request = new OperationRequest(
                "test", 1L, 1L
        );

        when(operationService.createOperation(request)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                post("/api/operation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isNotFound()
        );



    }

    @Test
    void shouldReturn400WhenInvalidDataUpdateOperation() throws Exception {

        Long id = 1L;
        String blankName = "";

        OperationRequest request = new OperationRequest(
                blankName, 1L, 1L
        );


        mockMvc.perform(
                put("/api/operation/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        );



    }

    @Test
    void shouldReturn404WhenNotFoundUpdateOperation() throws Exception {

        Long id = 1L;


        OperationRequest request = new OperationRequest(
                "test", 1L, 1L
        );

        doThrow(new ResourceNotFoundException("")).when(operationService).updateOperation(id, request);

        mockMvc.perform(
                put("/api/operation/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isNotFound()
        );



    }


    @Test
    void shouldReturn400WhenInvalidIdDeleteOperation() throws Exception {

        Long invalidId = -1L;

        mockMvc.perform(
                delete("/api/operation/{id}", invalidId)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn404WhenNotFoundDeleteOperation() throws Exception {

        Long id = 1L;


        doThrow(new ResourceNotFoundException("")).when(operationService).deleteOperation(id);

        mockMvc.perform(
                delete("/api/operation/{id}", id)
        ).andExpect(
                status().isNotFound()
        );



    }

}
