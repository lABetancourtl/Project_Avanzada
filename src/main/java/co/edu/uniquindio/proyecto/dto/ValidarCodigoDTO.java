package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ValidarCodigoDTO(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    String email,
    
    @NotBlank(message = "El código es obligatorio")
    String codigo
    ){

}