package co.edu.uniquindio.proyecto.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document("comentarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comentario {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    private ObjectId reporteId;
    private String mensaje;
    private LocalDateTime fecha;
    private ObjectId usuarioId;
}
