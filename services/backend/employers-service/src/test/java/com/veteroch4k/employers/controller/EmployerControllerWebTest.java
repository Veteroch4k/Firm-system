package com.veteroch4k.employers.controller;

import com.veteroch4k.employers.controllers.EmployerController;
import com.veteroch4k.employers.services.EmployerService;
import com.veteroch4k.firm.starter.exceptions.GlobalExceptionHandler;
import com.veteroch4k.firm.starter.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployerController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
public class EmployerControllerWebTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EmployerService employerService;


    @Autowired
    private ApplicationContext context;

    @BeforeEach
    void printBeans() {
        System.out.println("=== GlobalExceptionHandler beans ===");
        for (String name : context.getBeanNamesForType(
                com.veteroch4k.firm.starter.exceptions.GlobalExceptionHandler.class)) {
            System.out.println("Found: " + name);
        }
        System.out.println("=== ControllerAdvice beans ===");
        for (String name : context.getBeanNamesForAnnotation(
                org.springframework.web.bind.annotation.ControllerAdvice.class)) {
            System.out.println("Advice: " + name);
        }
    }


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

        when(employerService.findEmployerById(id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/employers/{id}", id)
        ).andExpect(
                status().isNotFound()
        );

    }


    @Test
    void shouldReturn404WhenEmptyTableGetRandomEmployer() throws Exception {

        when(employerService.getRandomEmployer()).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/employers/random")
        ).andExpect(
                status().isNotFound()
        );

    }
}
