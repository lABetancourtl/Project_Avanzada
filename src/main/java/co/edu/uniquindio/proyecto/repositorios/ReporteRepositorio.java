package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.Reporte;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteRepositorio extends MongoRepository<Reporte, ObjectId> {
    
    List<Reporte> findByUsuarioId(ObjectId usuarioId);
    
    List<Reporte> findByCiudad(String ciudad);
    
    List<Reporte> findByCategoria(String categoria);
    
    List<Reporte> findByEstado(EstadoReporte estado);
    
    List<Reporte> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
    
    List<Reporte> findByTituloContainingOrDescripcionContaining(String titulo, String descripcion);
    
    List<Reporte> findByCiudadAndCategoria(String ciudad, String categoria);
    
    List<Reporte> findByUsuarioIdAndEstado(ObjectId usuarioId, EstadoReporte estado);
}