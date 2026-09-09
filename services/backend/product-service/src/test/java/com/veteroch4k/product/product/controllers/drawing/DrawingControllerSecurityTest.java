package com.veteroch4k.product.product.controllers.drawing;

import com.veteroch4k.product.configs.SecurityConfig;
import com.veteroch4k.product.controllers.DrawingController;
import com.veteroch4k.product.services.DrawingService;
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

@WebMvcTest(controllers = DrawingController.class)
@Import(SecurityConfig.class)
public class DrawingControllerSecurityTest {

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private DrawingService drawingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAccessWhenGetDrawings() throws Exception {
        mockMvc.perform(
                get("/api/drawing/all")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    void shouldDenyAccess401WhenGetDrawings() throws Exception {
        mockMvc.perform(
                get("/api/drawing/all")
        ).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    void shouldAccessWhenGetDrawing() throws Exception {
        mockMvc.perform(
                get("/api/drawing/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    void shouldDenyAccess401WhenGetDrawing() throws Exception {
        mockMvc.perform(
                get("/api/drawing/1")
        ).andExpect(
                status().isUnauthorized()
        );
    }
}
