package com.veteroch4k.order.controllers;

import com.veteroch4k.order.controller.OrderController;
import com.veteroch4k.order.exceptions.ResourceNotFoundException;
import com.veteroch4k.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerWebTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    OrderService orderService;

    @Test
    void shouldReturn200WhenGetAllOrders() throws Exception {

        mockMvc.perform(
                        get("/api/order/all")
                                .param("page", "1")
                                .param("size", "10")
                )
                .andExpectAll(
                        status().isOk()
                );

    }

    @Test
    void shouldReturn400WhenBadParamWhenGetAllOrders() throws Exception {

        mockMvc.perform(
                get("/api/order/all")
                        .param("page", "0")
                        .param("size", "-10")
        ).andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    void shouldReturn400WhenBadPageWhenCreateOrderWithNegativeQuantity() throws Exception {

        String invalidJson = """
                {
                "productId": 1,
                "quantity": -1
                }
                """;

        mockMvc.perform(
                post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson)
        ).andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    void shouldReturn400WhenGetOrdersByDateWithInvalidDate() throws Exception {

        mockMvc.perform(
                get("/api/order/by-date")
                        .param("date", "2026-09-31") //  В сентрябре 30 дней
        ).andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    void shouldReturn400WhengetOrdersByDateRangeWithInvalidDates() throws Exception {


        String start = "2026-08-30";
        String end = "2026-07-30";

        when(orderService.findByOrdersByDateBetween(any(), any(), any()))
                .thenThrow(new IllegalArgumentException(""));

        mockMvc.perform(
                get("/api/order/between-dates")
                        .param("start", start)
                        .param("end", end)
        ).andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    void ShouldReturn400WhenGetOrderByIdWithInvalidId() throws Exception {
        mockMvc.perform(
                get("/api/order/{id}", "-1")

        ).andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    void ShouldReturn404WhenGetOrderByIdNoExists() throws Exception {

        Long id = 1L;

        when(orderService.findOrderById(id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(
                get("/api/order/{id}", String.valueOf(id))

        ).andExpectAll(
                status().isNotFound()
        );
    }


}
