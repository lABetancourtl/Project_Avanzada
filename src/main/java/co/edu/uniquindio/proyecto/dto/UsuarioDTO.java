package co.edu.uniquindio.proyecto.dto;

public record UsuarioDTO(
        String id,
        String nombre,
        String telefono,
        String ciudad,
        String direccion,
        String email,
        String rol,
        String estado
) {
}
