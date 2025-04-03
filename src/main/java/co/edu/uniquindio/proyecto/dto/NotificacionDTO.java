package co.edu.uniquindio.proyecto.dto;

import java.time.LocalDateTime;

public record NotificacionDTO(
    String id,
    String mensaje,
    LocalDateTime fecha,
    boolean leida,
    String tipo,
    String reporteId,
    String idUsuario
    ){
}