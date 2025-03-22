package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record EditarUsuarioDTO(
        @NotBlank
        String id,

        @NotBlank @Length(max = 100)
        String nombre,

        @Length(max = 15)
        String telefono,

        @NotBlank @Length(max = 100)
        String ciudad,

        @NotBlank @Length(max = 100)
        String direccion
) {
}

