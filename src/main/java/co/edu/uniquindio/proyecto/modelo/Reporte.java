package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoReporte;
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
    private ObjectId idUsuario; // cliente que lo creó
    private Ubicacion ubicacion;
    private CategoriaReporte categoria;
    private LocalDateTime fechaCreacion;
    private double latitud;
    private double longitud;
    private List<String> imagenes; // URLs desde Cloudinary o similar
    private EstadoReporte estadoActual;
    private int cantidadImportante; // votos
    private boolean activo; // borrado lógico
}

