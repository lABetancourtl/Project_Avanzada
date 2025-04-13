package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record CambiarEstadoDTO(
        @NotBlank(message = "El estado es obligatorio")
        String nuevoEstado
) {
}
