package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record CrearCategoriaDTO(

    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    String icono // puede ser opcional, por eso no usamos @NotBlank
){

}
