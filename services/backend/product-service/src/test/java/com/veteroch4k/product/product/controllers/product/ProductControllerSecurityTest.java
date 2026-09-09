package com.veteroch4k.product.product.controllers.product;

import com.veteroch4k.product.configs.SecurityConfig;
import com.veteroch4k.product.controllers.ProductController;
import com.veteroch4k.product.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@Import({SecurityConfig.class})
public class ProductControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private ProductService productService;

    @Test
    void shouldAccessWhenGetProducts() throws Exception {

        mockMvc.perform(
                get("/api/product/all")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    void shouldDenyAccess401WhenGetProducts() throws Exception {

        mockMvc.perform(
                get("/api/product/all")
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldAccessWhenGetProduct() throws Exception {

        mockMvc.perform(
                get("/api/product/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    void shouldDenyAccess401WhenGetProduct() throws Exception {

        mockMvc.perform(
                get("/api/product/1")
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldAccessWhenGetManufacturingInfo() throws Exception {

        mockMvc.perform(
                get("/api/product/1/manufacturing-info")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    void shouldDenyAccess401WhenGetManufacturingInfo() throws Exception {

        mockMvc.perform(
                get("/api/product/1/manufacturing-info")
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldDenyAccess403WhenGetManufacturingInfo() throws Exception {

        mockMvc.perform(
                get("/api/product/1/manufacturing-info")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
        ).andExpect(
                status().isForbidden()
        );

    }



}
