package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.enums.Rol;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
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

    private String nombre;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String email;
    private String password;
    private Rol rol; // CLIENTE o MODERADOR
    private EstadoUsuario estado; // ACTIVO, INACTIVO, ELIMINADO
    private LocalDateTime fechaRegistro;
    private String codigoValidacion;
    private LocalDateTime expiracionCodigo;
    private boolean activo; // para borrado lógico
}
