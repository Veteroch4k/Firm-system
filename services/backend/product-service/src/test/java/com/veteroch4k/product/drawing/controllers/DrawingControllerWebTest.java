package com.veteroch4k.product.drawing.controllers;

import com.veteroch4k.product.controllers.DrawingController;
import com.veteroch4k.product.exceptions.ResourceNotFoundException;
import com.veteroch4k.product.services.DrawingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DrawingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DrawingControllerWebTest {

    @MockitoBean
    private DrawingService drawingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn400WhenBadParamGetDrawings() throws Exception {

        String invalidPage = "-1";

        mockMvc.perform(
                get("/api/drawing/all")
                        .param("page", invalidPage)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn400WhenBadParamGetDrawing() throws Exception {
        String invalidId = "-1";

        mockMvc.perform(
                get("/api/drawing/{id}", invalidId)
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    void shouldReturn404WhenNotFoundGetDrawing() throws Exception {
        Long id = 1L;

        when(drawingService.findDrawingById(id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/drawing/{id}", id)
        ).andExpect(
                status().isNotFound()
        );
    }
}
