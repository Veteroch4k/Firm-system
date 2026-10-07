package com.veteroch4k.firm.starter.configs;

import com.veteroch4k.firm.starter.otlp.InstallOpenTelemetryAppender;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass({OpenTelemetry.class, OpenTelemetryAppender.class})
public class FirmTracingAutoConfiguration {

    @Bean
    public InstallOpenTelemetryAppender installOpenTelemetryAppender(OpenTelemetry openTelemetry) {
        return new InstallOpenTelemetryAppender(openTelemetry);
    }
}
