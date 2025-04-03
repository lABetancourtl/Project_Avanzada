package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record EstadisticaDTO (
        String nombre,
        Long cantidad,
        Double porcentaje
){}