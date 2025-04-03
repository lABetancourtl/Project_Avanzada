package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteRepositorio extends MongoRepository<Reporte, ObjectId> {

    // Buscar reportes por el ID del usuario que los creó
    List<Reporte> findByClienteId(ObjectId usuarioId);

    // Buscar reportes por estado
    List<Reporte> findByEstadoActual(String estadoActual);

    // Buscar reportes que contengan una palabra clave en el título
    List<Reporte> findByTituloContainingIgnoreCase(String titulo);

    // Buscar reportes en una categoría específica
    List<Reporte> findByCategoriaId(ObjectId categoriaId);
}