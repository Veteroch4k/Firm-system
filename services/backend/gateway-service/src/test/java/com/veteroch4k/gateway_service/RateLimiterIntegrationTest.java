package com.veteroch4k.gateway_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.stream.IntStream;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerCfg.class})
public class RateLimiterIntegrationTest {

    private WebTestClient webClient;

    @MockitoBean
    private ReactiveClientRegistrationRepository clientRegistrationRepository;

    @MockitoBean
    private ReactiveJwtDecoder reactiveJwtDecoder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void shouldReturn429_whenRateLimitExceeded() {
        int burstCapacity = 20;
        String testUrl = "product/api/product/all";

        IntStream.range(0, burstCapacity).parallel().forEach(i ->
                webClient.get()
                        .uri(testUrl)
                        .header("X-Forwarded-For", "192.168.1.100")
                        .exchange()
                        .expectStatus().isNotFound()
        );

        webClient.get()
                .uri(testUrl)
                .header("X-Forwarded-For", "192.168.1.100")
                .exchange()
                .expectStatus().isEqualTo(429);

        webClient.get()
                .uri(testUrl)
                .header("X-Forwarded-For", "10.0.0.5")
                .exchange()
                .expectStatus().isNotFound();
    }

}


