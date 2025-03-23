package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
    private String id;
    private String mensaje;
    private LocalDateTime fecha;
    private boolean leida;
    private String tipo;
    private String reporteId;
    private String idUsuario;
}