package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.Categoria;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepositorio extends MongoRepository<Categoria, ObjectId> {
    
    Optional<Categoria> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
}