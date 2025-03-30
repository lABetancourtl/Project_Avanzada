package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.documents.Ubicacion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UbicacionRepositorio extends MongoRepository<Ubicacion, ObjectId> {
}
