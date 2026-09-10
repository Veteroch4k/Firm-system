package com.veteroch4k.factory_service.controller.factory;

import com.veteroch4k.factory_service.configs.SecurityConfig;
import com.veteroch4k.factory_service.controller.FactoryController;
import com.veteroch4k.factory_service.services.FactoryService;
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

@WebMvcTest(controllers = FactoryController.class)
@Import({SecurityConfig.class})
public class FactoryControllerSecurityTest {

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private FactoryService factoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAccessGetFactories() throws Exception {

        mockMvc.perform(
                get("/api/factory/all")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    void shouldDenyAccess401GetFactories() throws Exception {

        mockMvc.perform(
                get("/api/factory/all")
        ).andExpect(
                status().isUnauthorized()
        );


    }

    @Test
    void shouldAccessGetFactory() throws Exception {

        mockMvc.perform(
                get("/api/factory/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
        ).andExpect(
                status().isOk()
        );


    }

    @Test
    void shouldDenyAccess401GetFactory() throws Exception {

        mockMvc.perform(
                get("/api/factory/1")
        ).andExpect(
                status().isUnauthorized()
        );

    }
}
