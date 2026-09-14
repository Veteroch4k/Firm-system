package com.veteroch4k.toolwarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication(scanBasePackages = {
		"com.veteroch4k.toolwarehouse",
		"com.veteroch4k.firm.common"
})
@RefreshScope
public class ToolwarehouseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolwarehouseServiceApplication.class, args);
	}

}
