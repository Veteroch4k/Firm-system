package com.veteroch4k.product.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Product Service API",
                description = "API сервиса товаров",
                version = "1.0",
                contact = @Contact(
                        name = "Popov Victor",
                        email = "viktor.popov2005@mail.ru",
                        url = "https://github.com/Veteroch4k"
                )
        )
)
@Component
public class OpenApiConfig {


    @Bean
    public OpenApiCustomizer serverUrlCustomizer() {
        return openApi -> openApi.setServers(
                List.of(new Server().url("/product").description("via gateway"))
        );
    }
}
