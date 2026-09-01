package com.veteroch4k.warehouse.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(

        LocalDateTime time,

        int status,

        String error,

        String message
) {
}
