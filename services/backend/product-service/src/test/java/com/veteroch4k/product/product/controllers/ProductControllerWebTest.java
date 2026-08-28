package com.veteroch4k.product.product.controllers;

import com.veteroch4k.product.controllers.ProductController;
import com.veteroch4k.product.dto.product.ProductResponse;
import com.veteroch4k.product.exceptions.ResourceNotFoundException;
import com.veteroch4k.product.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerWebTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProductService productService;

    void shouldReturn400WhenBadParamGetProducts() throws Exception {

        String InvalidSize = "0";

        mockMvc.perform(
                get("/api/product/all")
                        .param("page", "1")
                        .param("size", InvalidSize)

        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    void shouldReturn400WhenBadParamGetProduct() throws Exception {

        String invalidId = "-1";

        mockMvc.perform(
                get("/api/product/{id}", invalidId)

        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn404WhenNoFoundGetProduct() throws Exception {

        Long id = 1L;

        when(productService.findProductById(id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/product/{id}", id)
        ).andExpect(
                status().isNotFound()
        );

    }

    @Test
    void shouldReturn400WhenBadParamgGetManufacturingInfo() throws Exception {

        String invalidId = "-1";

        mockMvc.perform(
                get("/api/product/{id}/manufacturing-info", invalidId)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void shouldReturn404WhenNoFoundGetManufacturingInfo() throws Exception {
        Long id = 1L;

        when(productService.getProductInfo(id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/product/{id}/manufacturing-info", id)
        ).andExpect(
                status().isNotFound()
        );

    }

}
