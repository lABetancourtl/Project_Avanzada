package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearCategoriaDTO(

    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    String icono // puede ser opcional, por eso no usamos @NotBlank
){

}
