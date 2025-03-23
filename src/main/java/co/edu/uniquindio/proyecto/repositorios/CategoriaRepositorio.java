package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.CategoriaReporte;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepositorio extends MongoRepository<CategoriaReporte, ObjectId> {
    
    Optional<CategoriaReporte> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
}