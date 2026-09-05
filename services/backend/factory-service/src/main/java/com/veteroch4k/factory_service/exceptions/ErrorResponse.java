package com.veteroch4k.factory_service.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime time,
        int status,
        String error,
        String message
) {
}
