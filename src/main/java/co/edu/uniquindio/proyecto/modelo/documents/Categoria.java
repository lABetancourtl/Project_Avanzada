package co.edu.uniquindio.proyecto.modelo.documents;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    private String nombre;
    private String descripcion;
}
