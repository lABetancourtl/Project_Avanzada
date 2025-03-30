package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.vo.HistorialReporte;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistorialReporteRepositorio extends MongoRepository<HistorialReporte, ObjectId> {
}
