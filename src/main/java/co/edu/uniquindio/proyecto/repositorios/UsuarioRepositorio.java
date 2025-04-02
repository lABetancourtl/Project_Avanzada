package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends MongoRepository<Usuario, ObjectId> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByNombreContaining(String nombre);

    List<Usuario> findByCiudad(String ciudad);

    List<Usuario> findByNombreContainingAndCiudad(String nombre, String ciudad);

    //ya acepta paginación
    List<Usuario> findByEstado(EstadoUsuario estado, Pageable pageable);

    List<Usuario> findByNombreContainingIgnoreCaseAndEstado(String nombre, EstadoUsuario estado, Pageable pageable);

    List<Usuario> findByCiudadContainingIgnoreCaseAndEstado(String ciudad, EstadoUsuario estado, Pageable pageable);

    List<Usuario> findByNombreContainingIgnoreCaseAndCiudadContainingIgnoreCaseAndEstado(
            String nombre, String ciudad, EstadoUsuario estado, Pageable pageable);

    boolean existsByEmail(String email);
}
