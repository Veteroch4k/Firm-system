package com.veteroch4k.order.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Order Service API",
                description = "API сервиса заказов",
                version = "1.0",
                contact = @Contact(
                        name = "Popov Victor",
                        email = "viktor.popov2005@mail.ru",
                        url = "https://github.com/Veteroch4k"
                )
        )
)
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer serverUrlCustomizer() {
        return openApi -> openApi.setServers(
                List.of(new Server().url("/order").description("via gateway"))
        );
    }


}
