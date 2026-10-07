package com.veteroch4k.firm.starter.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime time,

        int status,

        String error,

        String message
) {
}
