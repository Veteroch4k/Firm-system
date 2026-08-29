package com.veteroch4k.employers.exceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime time,

        int status,

        String error,

        String message
) {
}
