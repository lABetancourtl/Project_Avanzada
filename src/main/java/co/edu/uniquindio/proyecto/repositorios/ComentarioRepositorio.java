package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.documents.Comentario;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ComentarioRepositorio extends MongoRepository<Comentario, ObjectId> {

    List<Comentario> findByReporteId(ObjectId reporteId);

    List<Comentario> findByUsuarioId(ObjectId usuarioId);

    void deleteByReporteId(ObjectId reporteId);

    long countByReporteId(ObjectId reporteId);
}
