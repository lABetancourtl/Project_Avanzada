package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.excepciones.RecursoNoEncontradoException;
import co.edu.uniquindio.proyecto.mapper.UsuarioMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public void crear(CrearUsuarioDTO crearUsuarioDTO) throws Exception {
        if( existeEmail(crearUsuarioDTO.email()) ){
            throw new Exception("El correo "+crearUsuarioDTO.email()+" ya está en uso");
        }
        Usuario usuario = usuarioMapper.toDocument(crearUsuarioDTO);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void editar(EditarUsuarioDTO editarUsuarioDTO) throws Exception {
        Usuario usuario = obtenerUsuarioPorId(editarUsuarioDTO.id());
        usuarioMapper.editarUsuarioDTO(editarUsuarioDTO, usuario);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new Exception("ID inválido: " + id);
        }
        Usuario usuario = obtenerUsuarioPorId(id);
        usuario.setEstado(EstadoUsuario.ELIMINADO);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public UsuarioDTO obtener(String id) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new Exception("ID inválido: " + id);
        }
        Usuario usuario = obtenerUsuarioPorId(id);
        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public  List<UsuarioDTO> listarTodos(String nombre, String ciudad, int pagina) {
        if(pagina < 0) throw new RuntimeException("La página no puede ser menor a 0");
        // Crear criterios dinámicos
        Criteria criteria = new Criteria();
        if (nombre != null && !nombre.isEmpty()) {
            criteria.and("nombre").regex(nombre, "i"); // Ignora a mayúsculas/minúsculas
        }
        if (ciudad!= null && !ciudad.isEmpty()) {
            criteria.and("ciudad").regex(ciudad, "i");
        }
        // Crear la consulta con los criterios y la paginación de 5 elementos por página
        Query query = new Query(criteria).with(PageRequest.of(pagina, 5));
        List<Usuario> usuarios = mongoTemplate.find(query, Usuario.class);
        // Convertir la lista de usuarios a una lista de DTOs
        return usuarios.stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    @Override
    public void enviarCodigoVerificacion(String email) throws Exception {

    }

    @Override
    public void cambiarPassword(String email, CambiarPasswordDTO cambiarPasswordDTO) throws Exception {

    }

    @Override
    public void activarCuenta(String email, ActivarCuentaDTO activarCuentaDTO) throws Exception {

    }

    @Override
    public List<InfoReporteDTO> obtenerReportesUsuario(String id) {

        return null;
    }

    private boolean existeEmail(String email) {
        return usuarioRepositorio.findByEmail(email).isPresent();
    }

    //Metodo utilizado en crear, editar y eliminar (Metodo de apoyo para no repetir codigo)
    private Usuario obtenerUsuarioPorId(String id) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new RecursoNoEncontradoException("El ID proporcionado no es válido: " + id);
        }
        ObjectId objectId = new ObjectId(id);
        return usuarioRepositorio.findById(objectId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el usuario con el id " + id));
    }


}
