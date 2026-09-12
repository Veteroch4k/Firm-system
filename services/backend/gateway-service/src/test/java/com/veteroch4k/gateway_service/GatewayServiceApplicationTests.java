package com.veteroch4k.gateway_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class GatewayServiceApplicationTests {

	@MockitoBean
	private ReactiveClientRegistrationRepository clientRegistrationRepository;

	@MockitoBean
	private ReactiveJwtDecoder reactiveJwtDecoder;

	@Test
	void contextLoads() {
	}

}
