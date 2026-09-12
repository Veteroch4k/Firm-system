package com.Veteroc4k.config_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.config.server.environment.vault.SpringVaultEnvironmentRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ConfigServiceApplicationTests {

	@MockitoBean
	private SpringVaultEnvironmentRepository repository;

	@Test
	void contextLoads() {
	}

}
