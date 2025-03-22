package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CrearUsuarioDTO(

        @NotBlank @Length(max = 100) String nombre,
        @NotBlank @Length(max = 100) String ciudad,
        @NotBlank @Length(max = 100) String direccion,
        @Length(max = 15) String telefono,
        @NotBlank @Email @Length(max = 100) String email,
        @NotBlank @Length(min = 7, max = 20) String password
) {

}
