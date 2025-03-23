package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditarReporteDTO {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    private UbicacionDTO ubicacion;
    
    private String categoriaId;
    
    private boolean importante;
    
    private List<String> fotos;
}