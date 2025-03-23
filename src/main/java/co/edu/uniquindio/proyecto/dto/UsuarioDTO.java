package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private String ciudad;
    private String direccion;
    private String estado;
    private String rol;
}