package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.documents.Notificacion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepositorio extends MongoRepository<Notificacion, ObjectId> {
    
    List<Notificacion> findByUsuarioId(ObjectId usuarioId);
    
    List<Notificacion> findByUsuarioIdAndLeida(ObjectId usuarioId, boolean leida);
    
    long countByUsuarioIdAndLeida(ObjectId usuarioId, boolean leida);
}