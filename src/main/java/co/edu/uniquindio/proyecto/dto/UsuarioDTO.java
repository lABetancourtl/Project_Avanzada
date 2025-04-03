package co.edu.uniquindio.proyecto.dto;

public record UsuarioDTO(
        String id,
        String nombre,
        String email,
        String telefono,
        String ciudad,
        String direccion,
        String estado,
        String rol
    ) {

}