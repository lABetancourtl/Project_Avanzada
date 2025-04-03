package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;


public record CrearComentarioDTO(
        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,

        @NotBlank(message = "El ID del cliente es obligatorio")
        String clienteId,

        @NotBlank(message = "El ID del reporte es obligatorio")
        String reporteId
    ) {
}