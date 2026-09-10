package com.veteroch4k.toolwarehouse.controller;

import com.veteroch4k.toolwarehouse.configs.SecurityConfig;
import com.veteroch4k.toolwarehouse.controllers.ToolController;
import com.veteroch4k.toolwarehouse.dto.ToolRequest;
import com.veteroch4k.toolwarehouse.services.ToolService;
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

@WebMvcTest(controllers = ToolController.class)
@Import({SecurityConfig.class})
public class ToolControllerSecurityTest {

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private ToolService toolService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAccessGetTools() throws Exception {

        mockMvc.perform(
                get("/api/tool/all")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    void shouldDenyAccess401GetTools() throws Exception {

        mockMvc.perform(
                get("/api/tool/all")
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldAccessGetTool() throws Exception {

        mockMvc.perform(
                get("/api/tool/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    void shouldDenyAccess401GetTool() throws Exception {

        mockMvc.perform(
                get("/api/tool/1")
        ).andExpect(
                status().isUnauthorized()
        );

    }


    @Test
    void shouldAccessGetToolsByTypeName() throws Exception {

        mockMvc.perform(
                get("/api/tool/by-type-name")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .param("typeName", "Test")
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    void shouldDenyAccess401GetToolsByTypeName() throws Exception {

        mockMvc.perform(
                get("/api/tool/by-type-name")
                        .param("typeName", "Test")
        ).andExpect(
                status().isUnauthorized()
        );

    }



    @Test
    void shouldAccessCreateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(1L);

        mockMvc.perform(
                post("/api/tool")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isCreated()
        );

    }

    @Test
    void shouldDenyAccess401CreateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(1L);

        mockMvc.perform(
                post("/api/tool")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldDenyAccess403CreateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(1L);

        mockMvc.perform(
                post("/api/tool")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isForbidden()
        );

    }


    @Test
    void shouldAccessUpdateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(1L);

        mockMvc.perform(
                put("/api/tool/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isNoContent()
        );


    }

    @Test
    void shouldDenyAccess401UpdateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(1L);

        mockMvc.perform(
                put("/api/tool/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldDenyAccess403UpdateTool() throws Exception {

        ToolRequest toolRequest = new ToolRequest(1L);

        mockMvc.perform(
                put("/api/tool/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toolRequest))
        ).andExpect(
                status().isForbidden()
        );


    }

    @Test
    void shouldAccessDeleteTool() throws Exception {


        mockMvc.perform(
                delete("/api/tool/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isNoContent()
        );

    }

    @Test
    void shouldDenyAccess401DeleteTool() throws Exception {

        mockMvc.perform(
                delete("/api/tool/1")
        ).andExpect(
                status().isUnauthorized()
        );

    }

    @Test
    void shouldDenyAccess403DeleteTool() throws Exception {

        mockMvc.perform(
                delete("/api/tool/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
        ).andExpect(
                status().isForbidden()
        );

    }


}
