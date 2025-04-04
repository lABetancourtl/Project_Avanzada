package co.edu.uniquindio.proyecto.dto;

import lombok.*;

public record UsuarioDTO (
        String id,
        String nombre,
        String email,
        String telefono,
        String ciudad,
        String estado,
        String rol,
        String fechaRegistro
){}