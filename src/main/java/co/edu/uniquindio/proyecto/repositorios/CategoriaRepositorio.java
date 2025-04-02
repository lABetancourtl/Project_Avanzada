package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.documents.Categoria;
import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepositorio extends MongoRepository<Categoria, ObjectId> {
    
    Optional<Categoria> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);

    boolean existsByNombreIgnoreCase(@NotBlank(message = "El nombre es obligatorio") String nombre);
}