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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;


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
        AtomicInteger okCount = new AtomicInteger();
        AtomicInteger tooManyCount = new AtomicInteger();

        IntStream.range(0, 30).parallel().forEach(i -> {
            int status = webClient.get()
                    .uri("/product/api/product/all")
                    .header("X-Forwarded-For", "192.168.1.100")
                    .exchange()
                    .returnResult(Void.class)
                    .getStatus()
                    .value();

            if (status == 200) okCount.incrementAndGet();
            else if (status == 429) tooManyCount.incrementAndGet();
        });

        assertThat(okCount.get()).as("должно пройти хотя бы 15 из 30").isBetween(15, 20);
        assertThat(tooManyCount.get()).as("должно быть отсечено хотя бы 10 из 30").isGreaterThanOrEqualTo(10);

        webClient.get()
                .uri("/product/api/product/all")
                .header("X-Forwarded-For", "192.168.1.100")
                .exchange()
                .expectStatus().isEqualTo(429);

        webClient.get()
                .uri("/product/api/product/all")
                .header("X-Forwarded-For", "10.0.0.5")
                .exchange()
                .expectStatus().isOk();
    }

}


