package co.edu.uniquindio.proyecto.modelo.documents;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ubicaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ubicacion {

    private double latitud;
    private double longitud;
}
