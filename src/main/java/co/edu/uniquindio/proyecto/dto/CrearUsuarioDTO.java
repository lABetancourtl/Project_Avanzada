package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CrearUsuarioDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    String email,
    
    @NotBlank(message = "La contraseña es obligatoria")
    String password,
    
    @NotBlank(message = "El teléfono es obligatorio")
    String telefono,
    
    @NotBlank(message = "La ciudad es obligatoria")
    String ciudad,
    
    @NotBlank(message = "La dirección es obligatoria")
    String direccion
    ){

}