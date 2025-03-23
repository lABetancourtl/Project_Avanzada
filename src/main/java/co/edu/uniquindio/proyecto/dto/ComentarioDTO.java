package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {
    private String id;
    private String mensaje;
    private LocalDateTime fecha;
    private String clienteId;
    private String nombreCliente;
    private String reporteId;
}