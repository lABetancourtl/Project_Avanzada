package co.edu.uniquindio.proyecto.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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