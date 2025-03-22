package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CambiarClaveDTO(
        @NotBlank @Email @Length(max = 100)
        String email,

        @NotBlank @Length(min = 6, max = 10)
        String codigo,

        @NotBlank @Length(min = 7, max = 20)
        String nuevaPassword
) {
}
