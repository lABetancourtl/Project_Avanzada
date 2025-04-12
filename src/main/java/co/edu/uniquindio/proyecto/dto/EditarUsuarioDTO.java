package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;


public record EditarUsuarioDTO (

            @NotBlank(message = "El nombre es obligatorio")
            String nombre,

            @NotBlank(message = "El teléfono es obligatorio")
            String telefono,

            @NotBlank(message = "La ciudad es obligatoria")
            String ciudad,

            String fotoPerfil
){}