package com.veteroch4k.toolwarehouse.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Toolwarehouse Service API",
                description = "API сервиса склада инструментов",
                version = "1.0",
                contact = @Contact(
                        name = "Popov Victor",
                        email = "viktor.popov2005@mail.ru",
                        url = "https://github.com/Veteroch4k"
                )
        )
)
public class OpenApiConfig {
}
