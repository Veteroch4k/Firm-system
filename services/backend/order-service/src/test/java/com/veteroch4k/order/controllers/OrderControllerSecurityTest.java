package com.veteroch4k.order.controllers;

import com.veteroch4k.order.configs.SecurityConfig;
import com.veteroch4k.order.controller.OrderController;
import com.veteroch4k.order.dto.orderDTO.OrderRequestDTO;
import com.veteroch4k.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@Import(SecurityConfig.class)
public class OrderControllerSecurityTest {

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAccessWhenGetOrders() throws Exception {

        mockMvc.perform(
                get("/api/order/all")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    void shouldDenyAccess401WhenGetOrders() throws Exception {

        mockMvc.perform(
                get("/api/order/all")
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldAccessWhenCreateOrder() throws Exception {

        OrderRequestDTO requestDTO = new OrderRequestDTO(1L,1L);

        mockMvc.perform(
                post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
        ).andExpect(
                status().isCreated()
        );

    }

    @Test
    void shouldDenyAccess401WhenCreateOrder() throws Exception {

        mockMvc.perform(
                post("/api/order")
        ).andExpect(
                status().isUnauthorized()
        );

    }

}
