package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record CrearComentarioDTO (
        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,

        @NotBlank(message = "El ID del cliente es obligatorio")
        String clienteId

        //Va o no va ?
//        @NotBlank(message = "El ID del reporte es obligatorio")
//        String reporteId
){}