package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearReporteDTO {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotBlank(message = "El ID del cliente es obligatorio")
    private String clienteId;
    
    @NotNull(message = "La ubicación es obligatoria")
    private UbicacionDTO ubicacion;
    
    @NotBlank(message = "La categoría es obligatoria")
    private String categoriaId;
    
    private boolean importante;
    
    private List<String> fotos;
}