package com.veteroch4k.factory_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RefreshScope
public class FactoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FactoryServiceApplication.class, args);
	}

}
