package com.veteroch4k.toolwarehouse.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime time,
        int status,
        String error,

        String message
) {
}
