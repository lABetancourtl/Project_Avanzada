package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;


public record EditarUsuarioDTO (

            @NotBlank(message = "El ID es obligatorio")
            String id,

            @NotBlank(message = "El nombre es obligatorio")
            String nombre,

            @NotBlank(message = "El teléfono es obligatorio")
            String telefono,

            @NotBlank(message = "La ciudad es obligatoria")
            String ciudad,

            @NotBlank(message = "La dirección es obligatoria")
            String direccion
){}