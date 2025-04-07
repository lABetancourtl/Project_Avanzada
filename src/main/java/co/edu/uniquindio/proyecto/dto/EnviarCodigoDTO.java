package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EnviarCoditoDTO(
        @NotBlank @Email String email

        ) {}
