package com.veteroch4k.factory_service.configs;

import feign.Request;
import feign.Request.Options;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class ProductFeignConfig {

    @Bean
    public Options options() {
        // 2 секунды на коннект, 3 секунды на чтение ответа
        return new Request.Options(
                2, TimeUnit.SECONDS,
                3, TimeUnit.SECONDS,
                true
        );
    }
}
