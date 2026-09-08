package com.veteroch4k.employers.controller;

import com.veteroch4k.employers.configs.SecurityConfig;
import com.veteroch4k.employers.controllers.EmployerController;
import com.veteroch4k.employers.services.EmployerService;
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

@WebMvcTest(controllers = EmployerController.class)
@Import(SecurityConfig.class)
public class EmployerControllerSecurityTest {

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployerService employerService;

    @Test
    void shouldReturn401WhenNoToken() throws Exception {
        mockMvc.perform(get("/api/employers/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn200WhenUserAccessAll() throws Exception {
        mockMvc.perform(get("/api/employers/all")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200WhenUserAccessGetById() throws Exception {
        mockMvc.perform(get("/api/employers/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200WhenUserAccessRandom() throws Exception {
        mockMvc.perform(get("/api/employers/random")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn403WhenUserAccessTest() throws Exception {
        mockMvc.perform(get("/api/employers/test")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn200WhenAdminAccessTest() throws Exception {
        mockMvc.perform(get("/api/employers/test")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk());
    }

}
