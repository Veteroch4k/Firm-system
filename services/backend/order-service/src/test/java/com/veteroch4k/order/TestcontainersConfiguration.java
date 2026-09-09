package com.veteroch4k.order;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	KafkaContainer kafkaContainer() {
		return new KafkaContainer(DockerImageName.parse("apache/kafka-native:latest"));
	}

	@Bean
	@ServiceConnection
	PostgreSQLContainer postgresContainer() {
		return new PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
				.withInitScript("init.sql");
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		JwtDecoder jwtDecoder = Mockito.mock(JwtDecoder.class);

		Jwt jwt = Jwt.withTokenValue("dummy-token")
				.header("alg", "none")
				.claim("sub", "test-user")
				.claim("realm_access", Map.of("roles", List.of("USER")))
				.build();

		when(jwtDecoder.decode(anyString())).thenReturn(jwt);

		return jwtDecoder;
	}

}
