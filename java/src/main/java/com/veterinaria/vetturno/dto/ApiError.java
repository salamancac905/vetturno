package com.veterinaria.vetturno.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        int status,
        String mensaje,
        Map<String, String> erroresPorCampo,
        LocalDateTime timestamp
) {
}
