package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionDTO {
    @NotNull(message = "La latitud es obligatoria")
    private Double latitud;
    
    @NotNull(message = "La longitud es obligatoria")
    private Double longitud;
}
