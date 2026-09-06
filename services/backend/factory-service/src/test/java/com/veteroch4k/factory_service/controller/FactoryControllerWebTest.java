package com.veteroch4k.factory_service.controller;

import com.veteroch4k.factory_service.exceptions.ResourceNotFoundException;
import com.veteroch4k.factory_service.services.FactoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FactoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FactoryControllerWebTest {

    @MockitoBean
    private FactoryService factoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn400WhenBadParamGetFactories() throws Exception {

        String invalidSize = "105";

        mockMvc.perform(
                get("/api/factory/all")
                        .param("size", invalidSize)
        ).andExpect(
                status().isBadRequest()
        );



    }


    @Test
    void shouldReturn400WhenInvalidIdGetFactory() throws Exception {

        Long invalidId = -1L;

        mockMvc.perform(
                get("/api/factory/{id}", invalidId)
        ).andExpect(
                status().isBadRequest()
        );



    }

    @Test
    void shouldReturn404WhenNotFoundGetFactory() throws Exception {

        Long id = 1L;

        when(factoryService.findFactoryById(id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/factory/{id}", id)
        ).andExpect(
                status().isNotFound()
        );



    }

}
