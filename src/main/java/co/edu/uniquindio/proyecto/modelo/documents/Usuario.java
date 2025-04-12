package co.edu.uniquindio.proyecto.modelo.documents;

import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;
import co.edu.uniquindio.proyecto.modelo.vo.CodigoValidacion;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document("usuarios")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    private String nombre;
    private String email;
    private String telefono;
    private String ciudad;
    private Rol rol;
    private EstadoUsuario estado; // ACTIVO, INACTIVO, ELIMINADO
    private String password;
    private CodigoValidacion codigoValidacion; // ← embebida
    private String fechaRegistro;
    private String fotoPerfil;


    // También puedes eliminar este constructor si usas @Builder y no necesitas lógica extra
    @Builder
    public Usuario(String nombre, String email, String telefono, String ciudad, Rol rol,
                   EstadoUsuario estado, String password, CodigoValidacion codigoValidacion, String fechaRegistro, String fotoPerfil) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.rol = rol;
        this.estado = estado;
        this.password = password;
        this.codigoValidacion = codigoValidacion;
        this.fechaRegistro = fechaRegistro;
        this.fotoPerfil = fotoPerfil;
    }
}
