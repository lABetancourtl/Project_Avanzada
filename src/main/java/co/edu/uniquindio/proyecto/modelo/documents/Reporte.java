package co.edu.uniquindio.proyecto.modelo.documents;

import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;
import co.edu.uniquindio.proyecto.modelo.vo.HistorialReporte;
import co.edu.uniquindio.proyecto.modelo.vo.Ubicacion;
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
    
    private String titulo;
    private ObjectId categoriaid;
    private String descripcion;
    private Ubicacion ubicacion;
    private List<String> fotos;
    private ObjectId usuarioId; // Referencia al usuario que creó el reporte
    private LocalDateTime fecha;
    private List<HistorialReporte> historial;
    private EstadoReporte estadoActual;
    private int contadorImportante;

}