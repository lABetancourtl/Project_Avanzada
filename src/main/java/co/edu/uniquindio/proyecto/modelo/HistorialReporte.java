package co.edu.uniquindio.proyecto.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;

import java.time.LocalDateTime;

@Document("historial_Reporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistorialReporte {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    private String observacion;
    private ObjectId clienteId;
    private EstadoReporte estado;
    private LocalDateTime fecha;
}
