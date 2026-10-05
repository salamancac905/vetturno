package com.veterinaria.vetturno.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CitaRequest(
        @NotNull(message = "La fecha y hora son obligatorias")
        LocalDateTime fechaHora,

        @NotBlank(message = "El motivo es obligatorio")
        String motivo,

        @NotNull(message = "El mascotaId es obligatorio")
        Long mascotaId,

        @NotNull(message = "El veterinarioId es obligatorio")
        Long veterinarioId
) {
}
