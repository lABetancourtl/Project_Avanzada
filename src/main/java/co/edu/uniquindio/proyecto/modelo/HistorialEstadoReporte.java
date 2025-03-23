package co.edu.uniquindio.proyecto.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;

import java.time.LocalDateTime;
import java.util.List;
@Document("historial_estados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistorialEstadoReporte {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    private ObjectId idReporte;
    private EstadoReporte nuevoEstado;
    private LocalDateTime fechaCambio;
    private String observacion;
}
