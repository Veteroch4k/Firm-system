package com.veteroch4k.warehouse.controller;

import com.veteroch4k.warehouse.configs.SecurityConfig;
import com.veteroch4k.warehouse.controllers.MaterialController;
import com.veteroch4k.warehouse.dto.MaterialRequest;
import com.veteroch4k.warehouse.service.MaterialService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {MaterialController.class})
@Import(SecurityConfig.class)
public class MaterialControllerSecurityTest {

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private MaterialService materialService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAccessGetMaterials() throws Exception {

        mockMvc.perform(
                get("/api/material/all")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isOk()
        );


    }

    @Test
    void shouldDenyAccess401GetMaterials() throws Exception {

        mockMvc.perform(
                get("/api/material/all")
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldAccessGetMaterial() throws Exception {

        mockMvc.perform(
                get("/api/material/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isOk()
        );


    }

    @Test
    void shouldDenyAccess401GetMaterial() throws Exception {

        mockMvc.perform(
                get("/api/material/1")
        ).andExpect(
                status().isUnauthorized()
        );


    }


    @Test
    void shouldAccessCreateMaterial() throws Exception {

        MaterialRequest request = new MaterialRequest(
                "Test"
        );


        mockMvc.perform(
                post("/api/material")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isCreated()
        );


    }

    @Test
    void shouldDenyAccess401CreateMaterial() throws Exception {

        MaterialRequest request = new MaterialRequest(
                "Test"
        );


        mockMvc.perform(
                post("/api/material")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isUnauthorized()
        );


    }
    @Test
    void shouldDenyAccess403CreateMaterial() throws Exception {

        MaterialRequest request = new MaterialRequest(
                "Test"
        );


        mockMvc.perform(
                post("/api/material")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isForbidden()
        );


    }


    @Test
    void shouldAccessUpdateMaterial() throws Exception {

        MaterialRequest request = new MaterialRequest(
                "Test"
        );


        mockMvc.perform(
                put("/api/material/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isNoContent()
        );

    }

    @Test
    void shouldDenyAccess401UpdateMaterial() throws Exception {

        MaterialRequest request = new MaterialRequest(
                "Test"
        );


        mockMvc.perform(
                put("/api/material/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isUnauthorized()
        );


    }
    @Test
    void shouldDenyAccess403UpdateMaterial() throws Exception {

        MaterialRequest request = new MaterialRequest(
                "Test"
        );


        mockMvc.perform(
                put("/api/material/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isForbidden()
        );


    }

    @Test
    void shouldAccessDeleteMaterial() throws Exception {



        mockMvc.perform(
                delete("/api/material/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isNoContent()
        );


    }

    @Test
    void shouldDenyAccess401DeleteMaterial() throws Exception {

        mockMvc.perform(
                delete("/api/material/1")
        ).andExpect(
                status().isUnauthorized()
        );

    }
    @Test
    void shouldDenyAccess403DeleteMaterial() throws Exception {

        mockMvc.perform(
                delete("/api/material/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
        ).andExpect(
                status().isForbidden()
        );


    }
}
