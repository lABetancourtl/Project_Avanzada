package co.edu.uniquindio.proyecto.dto;


import java.time.LocalDateTime;


public record ComentarioDTO(
        String id,
        String mensaje,
        LocalDateTime fecha,
        String clienteId,
        String nombreCliente,
        String reporteId
    ) {

}