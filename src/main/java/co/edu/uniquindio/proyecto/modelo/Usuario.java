package co.edu.uniquindio.proyecto.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;

import java.time.LocalDateTime;

@Document("usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    private String email;
    private Rol rol; // CLIENTE o ADMINISTRADOR
    private String telefono;
    private String password;
    private String nombre;
    private String ciudad;
    private EstadoUsuario estado; // ACTIVO, INACTIVO, ELIMINADO
    private CodigoValidacion codigoValidacion; // ← embebida
    private String direccion;
    private boolean activo;


}
