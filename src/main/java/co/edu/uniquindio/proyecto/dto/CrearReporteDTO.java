package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
public record CrearReporteDTO(
        @NotBlank(message = "El título es obligatorio")
        String titulo,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,

 //       @NotBlank(message = "El ID del cliente es obligatorio")
   //     String clienteId,

        @NotNull(message = "La ubicación es obligatoria")
        UbicacionDTO ubicacion,

        @NotBlank(message = "La categoría es obligatoria")
        String categoriaId,

        boolean importante,

        //List<String> fotos
        String foto
){}