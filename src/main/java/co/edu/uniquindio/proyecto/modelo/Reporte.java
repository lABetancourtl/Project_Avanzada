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
    
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private EstadoReporte estado;
    private ObjectId usuarioId; // Referencia al usuario que creó el reporte
    private String ciudad;
    private String categoria;
    private Ubicacion ubicacion; // Documento embebido
    private List<Comentario> comentarios; // Lista de documentos embebidos
    private List<String> imagenes; // URLs de las imágenes
}