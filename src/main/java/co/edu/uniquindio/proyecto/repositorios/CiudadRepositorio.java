package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.enums.Ciudad;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CiudadRepositorio extends MongoRepository<Ciudad, ObjectId> {
    
    Optional<Ciudad> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
}