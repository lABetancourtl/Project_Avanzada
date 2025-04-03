package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record LoginDTO (
                @NotBlank(message = "El email es obligatorio")
                @Email(message = "El formato del email no es válido")
                String email,

                @NotBlank(message = "La contraseña es obligatoria")
                String password
){}