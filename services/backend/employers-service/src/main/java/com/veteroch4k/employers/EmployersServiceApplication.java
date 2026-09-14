package com.veteroch4k.employers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;


@SpringBootApplication(scanBasePackages = {
		"com.veteroch4k.employers",
		"com.veteroch4k.firm.common"
})
@RefreshScope
public class EmployersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployersServiceApplication.class, args);
	}

}
