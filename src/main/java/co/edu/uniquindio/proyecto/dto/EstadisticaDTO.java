package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaDTO {
    private String nombre;
    private Long cantidad;
    private Double porcentaje;
}