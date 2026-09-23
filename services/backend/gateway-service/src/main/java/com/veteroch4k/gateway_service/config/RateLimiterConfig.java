package com.veteroch4k.gateway_service.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.security.Principal;
import java.util.Objects;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> exchange.getPrincipal()
                .map(Principal::getName)
                .defaultIfEmpty(
                        exchange.getRequest().getHeaders().getFirst("X-Forwarded-For") != null
                                ? Objects.requireNonNull(exchange.getRequest().getHeaders().getFirst("X-Forwarded-For"))
                                : Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress()
                );
    }
}
