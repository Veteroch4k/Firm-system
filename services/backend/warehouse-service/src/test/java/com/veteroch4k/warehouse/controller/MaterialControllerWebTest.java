package com.veteroch4k.warehouse.controller;

import com.veteroch4k.warehouse.controllers.MaterialController;
import com.veteroch4k.warehouse.dto.MaterialRequest;
import com.veteroch4k.warehouse.exceptions.ResourceNotFoundException;
import com.veteroch4k.warehouse.service.MaterialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MaterialController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MaterialControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MaterialService materialService;


    @Test
    void shouldReturn400WhenInvalidParamGetMaterials() throws Exception {

        String invalidPage = "-1";

        mockMvc.perform(
                get("/api/material/all")
                        .param("page", invalidPage)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn400WhenInvalidIdGetMaterial() throws Exception {
        Long invalidId = -1L;

        mockMvc.perform(
                get("/api/material/{id}", invalidId)
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    void shouldReturn404WhenNoFoundGetMaterial() throws Exception {
        Long id = 1L;

        when(materialService.findMaterialById(id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/material/{id}", id)
        ).andExpect(
                status().isNotFound()
        );
    }

    @Test
    void shouldReturn400WhenNotValidDataCreateMaterial() throws Exception {

        String BlankString = "";

        MaterialRequest request = new MaterialRequest(BlankString);

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/api/material")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpect(
                status().isBadRequest()
        );


    }

    @Test
    void shouldReturn400WhenInvalidIdUpdateMaterial() throws Exception {

        Long invalidId = -1L;
        String name = "Test";

        MaterialRequest request = new MaterialRequest(name);

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                put("/api/material/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn400WhenInvalidDataUpdateMaterial() throws Exception {

        Long id = 1L;
        String blankName = "";

        MaterialRequest request = new MaterialRequest(blankName);

        String requeestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                put("/api/material/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requeestJson)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn404WhenNoFoundUpdateMaterial() throws Exception {
        Long id = 1L;
        String name = "Test";

        MaterialRequest request = new MaterialRequest(name);

        String requestJson = objectMapper.writeValueAsString(request);

        doThrow(new ResourceNotFoundException(""))
                .when(materialService).updateMaterialById(id, request);

        mockMvc.perform(
                put("/api/material/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpect(
                status().isNotFound()
        );

    }

    @Test
    void shouldReturn400WhenInvalidIdDeleteMaterial() throws Exception {

        Long invalidId = -1L;

        mockMvc.perform(
                delete("/api/material/{id}", invalidId)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn404WhenNoFoundDeleteMaterial() throws Exception {
        Long id = 1L;

        doThrow(new ResourceNotFoundException(""))
                .when(materialService).deleteMaterialById(id);

        mockMvc.perform(
                delete("/api/material/{id}", id)
        ).andExpect(
                status().isNotFound()
        );

    }



}
