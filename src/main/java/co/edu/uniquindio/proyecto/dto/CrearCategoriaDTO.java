package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearCategoriaDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String icono;
}