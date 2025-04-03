package co.edu.uniquindio.proyecto.modelo.documents;

import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;
import co.edu.uniquindio.proyecto.modelo.vo.CodigoValidacion;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    // También puedes eliminar este constructor si usas @Builder y no necesitas lógica extra
    @Builder
    public Usuario(String email, Rol rol, String telefono, String password, String nombre, String ciudad, EstadoUsuario estado, CodigoValidacion codigoValidacion, String direccion) {
        this.email = email;
        this.rol = rol;
        this.telefono = telefono;
        this.password = password;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigoValidacion = codigoValidacion;
        this.direccion = direccion;
    }
}
