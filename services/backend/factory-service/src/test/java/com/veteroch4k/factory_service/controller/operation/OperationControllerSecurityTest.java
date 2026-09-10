package com.veteroch4k.factory_service.controller.operation;


import com.veteroch4k.factory_service.configs.SecurityConfig;
import com.veteroch4k.factory_service.controller.OperationController;
import com.veteroch4k.factory_service.dto.operation.OperationRequest;
import com.veteroch4k.factory_service.services.FactoryService;
import com.veteroch4k.factory_service.services.OperationService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OperationController.class)
@Import({SecurityConfig.class})
public class OperationControllerSecurityTest {

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private OperationService operationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAccessGetOperations() throws Exception {

        mockMvc.perform(
                get("/api/operation/all")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    void shouldDenyAccess401GetOperations() throws Exception {

        mockMvc.perform(
                get("/api/operation/all")
        ).andExpect(
                status().isUnauthorized()
        );


    }

    @Test
    void shouldAccessGetOperation() throws Exception {

        mockMvc.perform(
                get("/api/operation/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isOk()
        );


    }

    @Test
    void shouldDenyAccess401GetOperation() throws Exception {

        mockMvc.perform(
                get("/api/operation/1")
        ).andExpect(
                status().isUnauthorized()
        );

    }


    @Test
    void shouldAccessCreateOperation() throws Exception {

        OperationRequest operationRequest = new OperationRequest(
                "Test", 1L, 1L
        );

        mockMvc.perform(
                post("/api/operation")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationRequest))
        ).andExpect(
                status().isCreated()
        );


    }

    @Test
    void shouldDenyAccess401CreateOperation() throws Exception {

        OperationRequest operationRequest = new OperationRequest(
                "Test", 1L, 1L
        );

        mockMvc.perform(
                post("/api/operation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationRequest))
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldDenyAccess403CreateOperation() throws Exception {

        OperationRequest operationRequest = new OperationRequest(
                "Test", 1L, 1L
        );

        mockMvc.perform(
                post("/api/operation")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationRequest))
        ).andExpect(
                status().isForbidden()
        );


    }

    @Test
    void shouldAccessUpdateOperation() throws Exception {

        OperationRequest operationRequest = new OperationRequest(
                "Test", 1L, 1L
        );

        mockMvc.perform(
                put("/api/operation/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationRequest))
        ).andExpect(
                status().isNoContent()
        );


    }

    @Test
    void shouldDenyAccess401UpdateOperation() throws Exception {

        OperationRequest operationRequest = new OperationRequest(
                "Test", 1L, 1L
        );

        mockMvc.perform(
                put("/api/operation/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationRequest))
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldDenyAccess403UpdateOperation() throws Exception {

        OperationRequest operationRequest = new OperationRequest(
                "Test", 1L, 1L
        );

        mockMvc.perform(
                put("/api/operation/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationRequest))
        ).andExpect(
                status().isForbidden()
        );


    }


    @Test
    void shouldAccessDeleteOperation() throws Exception {


        mockMvc.perform(
                delete("/api/operation/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isNoContent()
        );


    }

    @Test
    void shouldDenyAccess401DeleteOperation() throws Exception {

        mockMvc.perform(
                delete("/api/operation/1")
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldDenyAccess403DeleteOperation() throws Exception {


        mockMvc.perform(
                delete("/api/operation/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
        ).andExpect(
                status().isForbidden()
        );


    }
}
