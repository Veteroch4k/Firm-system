package com.veteroch4k.factory_service.service.factory;

import com.veteroch4k.factory_service.TestContainerCfg;
import com.veteroch4k.factory_service.models.ProductManufacturingInfo;
import com.veteroch4k.factory_service.services.ProductServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import tools.jackson.databind.ObjectMapper;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Import(TestContainerCfg.class)
@EnableWireMock(@ConfigureWireMock(port = 9999))
public class ProductServiceClientWireMockTest  {

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.openfeign.client.config.product-service.url",
                () -> "http://localhost:9999");
        registry.add("eureka.client.enabled", () -> "false");
    }

    @Test
    void shouldReturnProductInfo_when200Ok() {
        Long productId = 1L;
        ProductManufacturingInfo stub = new ProductManufacturingInfo(
                1L, "Test", 1L, 1L, 10L
        );

        stubFor(get(urlEqualTo("/api/product/" + productId + "/manufacturing-info"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(stub))));

        ProductManufacturingInfo result = productServiceClient.getManufacturingInfo(productId);

        assertThat(result).isNotNull();
        assertThat(result.operationId()).isEqualTo(10L);
    }

    @Test
    void shouldRetry_whenServerReturns500() {
        Long productId = 2L;

        stubFor(get(urlEqualTo("/api/product/" + productId + "/manufacturing-info"))
                .willReturn(aResponse()
                        .withStatus(500)));

        assertThrows(Exception.class, () -> {
            productServiceClient.getManufacturingInfo(productId);
        });

        verify(3, getRequestedFor(urlEqualTo("/api/product/" + productId + "/manufacturing-info")));
    }
}
