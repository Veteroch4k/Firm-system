package com.veteroch4k.toolwarehouse.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime time,
        int status,
        String error,
        String message
) {
}
