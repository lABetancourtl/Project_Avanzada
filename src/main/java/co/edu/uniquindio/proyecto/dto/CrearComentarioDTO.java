package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearComentarioDTO {
    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;
    
    @NotBlank(message = "El ID del cliente es obligatorio")
    private String clienteId;
    
    @NotBlank(message = "El ID del reporte es obligatorio")
    private String reporteId;
}