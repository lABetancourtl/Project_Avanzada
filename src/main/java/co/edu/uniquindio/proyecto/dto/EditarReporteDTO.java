package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public record EditarReporteDTO (

            @NotBlank(message = "El título es obligatorio")
            String titulo,

            @NotBlank(message = "La descripción es obligatoria")
            String descripcion,

            UbicacionDTO ubicacion,

            String categoriaId,

            boolean importante,

            List<String> fotos
){}