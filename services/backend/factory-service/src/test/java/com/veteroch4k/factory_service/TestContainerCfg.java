package com.veteroch4k.factory_service;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestContainerCfg {

	@Bean
	@ServiceConnection
	PostgreSQLContainer postgreSQLContainer() {
		return new PostgreSQLContainer(DockerImageName.parse("postgres:15"))
				.withInitScript("init.sql");
	}

	@Bean
	@ServiceConnection
	KafkaContainer kafkaContainer() {
		return new KafkaContainer(DockerImageName.parse("apache/kafka:latest"));
	}

	@Bean
	@ServiceConnection(name = "redis")
	public GenericContainer<?> redisContainer() {
		return new GenericContainer<>("redis:7-alpine")
				.withExposedPorts(6379);
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		JwtDecoder jwtDecoder = Mockito.mock(JwtDecoder.class);

		Jwt jwt = Jwt.withTokenValue("dummy-token")
				.header("alg", "none")
				.claim("sub", "test-user")
				.claim("realm_access", Map.of("roles", List.of("ADMIN")))
				.build();

		when(jwtDecoder.decode(anyString())).thenReturn(jwt);

		return jwtDecoder;
	}




}
