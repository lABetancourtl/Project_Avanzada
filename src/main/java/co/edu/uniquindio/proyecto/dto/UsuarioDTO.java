package co.edu.uniquindio.proyecto.dto;

public record UsuarioDTO(
        Long id,
        String nombre,
        String telefono,
        String ciudad,
        String direccion,
        String email
) {
}
