package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("reportes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reporte {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;
    
    private List<HistorialReporte> historial;
    private LocalDateTime fecha;
    private String descripcion;
    private int contadorImportante;
    private ObjectId usuarioId; // Referencia al usuario que creó el reporte
    private String titulo;
    private Ubicacion ubicacion;
    private ObjectId categoriaid;
    private List<String> fotos;
    private EstadoReporte estadoActual;
}