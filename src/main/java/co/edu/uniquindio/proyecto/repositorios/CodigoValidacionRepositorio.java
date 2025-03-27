package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.CodigoValidacion;
import co.edu.uniquindio.proyecto.modelo.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodigoValidacionRepositorio extends MongoRepository<CodigoValidacion, String> {
    
    Optional<CodigoValidacion> findByCodigoAndUsuarioEmail(String codigo, String email);
    
    Optional<CodigoValidacion> findByUsuario(Usuario usuario);
}