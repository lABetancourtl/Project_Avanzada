package co.edu.uniquindio.proyecto.modelo.documents;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.uniquindio.proyecto.modelo.enums.TipoNotificacion;

import java.time.LocalDateTime;

@Document("notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notificacion {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    private String mensaje;
    private LocalDateTime fecha;
    private TipoNotificacion tipoNotificacion;
    private boolean leida;
    private ObjectId reporteId;
    private ObjectId usuarioId;
}
