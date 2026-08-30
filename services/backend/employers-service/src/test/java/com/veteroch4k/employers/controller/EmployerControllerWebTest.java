package com.veteroch4k.employers.controller;

import com.veteroch4k.employers.controllers.EmployerController;
import com.veteroch4k.employers.services.EmployerSevice;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployerControllerWebTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EmployerSevice employerSevice;

    @Test
    void shouldReturn400WhenBadParamGetAllEmployers() throws Exception {

        String invalidPage = "-1";

        mockMvc.perform(
                get("/api/employers/all")
                        .param("page", invalidPage)

        ).andExpect(
                status().isBadRequest()
        );


    }

    @Test
    void shouldReturn400WhenInvalidIdGetEmployerById() throws Exception {

        Long invalidId = -1L;

        mockMvc.perform(
                get("/api/employers/{id}", invalidId)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn404WhenNotFoundGetEmployerById() throws Exception {

        Long id = 1L;

        when(employerSevice.findEmployerById(id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/employers/{id}", id)
        ).andExpect(
                status().isNotFound()
        );

    }


    @Test
    void shouldReturn404WhenEmptyTableGetRandomEmployer() throws Exception {

        when(employerSevice.getRandomEmployer()).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/employers/random")
        ).andExpect(
                status().isNotFound()
        );

    }
}
