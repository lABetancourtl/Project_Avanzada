package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.excepciones.RecursoNoEncontradoException;
import co.edu.uniquindio.proyecto.mapper.UsuarioMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.vo.CodigoValidacion;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.seguridad.JWTUtilsHelper;
import co.edu.uniquindio.proyecto.servicios.EmailServicio;
import co.edu.uniquindio.proyecto.servicios.ImagenServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import co.edu.uniquindio.proyecto.util.EmailTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final MongoTemplate mongoTemplate;
    private final EmailServicio emailServicio;
    private final ImagenServicio imagenServicio;
    EmailTemplateUtil EmailUtils;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtilsHelper jwtUtilsHelper;

    //Envia el codigo para cambiar password
    @Override
    public void enviarCodigoVerificacion(EnviarCodigoDTO enviarCodigoDTO) throws Exception {
        try {
            Usuario usuario = obtenerPorEmail(enviarCodigoDTO.email());

            String codigoCambiarClave = generarCodigo();
            usuario.setCodigoValidacion(new CodigoValidacion(
                    codigoCambiarClave,
                    LocalDateTime.now()
            ));
            usuarioRepositorio.save(usuario);

            String asunto = "Restablecer Password";
            String cuerpo = EmailUtils.generarTemplateRestablecerClave(
                    usuario.getNombre(),
                    usuario.getEmail(),
                    codigoCambiarClave
            );
            String destinatario = usuario.getEmail();

            emailServicio.enviarCorreo(new EmailDTO(asunto, cuerpo, destinatario));

        } catch (NoSuchElementException e) {
            throw new Exception("El correo no está registrado.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No se pudo enviar el correo: " + e.getMessage());
        }
    }


    @Override
    public void crear(CrearUsuarioDTO crearUsuarioDTO) throws Exception {
        if (existeEmail(crearUsuarioDTO.email())) {
            throw new Exception("El correo " + crearUsuarioDTO.email() + " ya está en uso");
        }
        Usuario usuario = usuarioMapper.toDocument(crearUsuarioDTO);
        // Cifrar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(crearUsuarioDTO.password()));
        // Generar código de activación
        String codigoActivacion = generarCodigo();
        usuario.setCodigoValidacion(new CodigoValidacion(
                codigoActivacion,
                LocalDateTime.now()
        ));
        // Guardar usuario
        usuarioRepositorio.save(usuario);
        // Preparar correo dinámico
        String asunto = "Verificación de cuenta";
        String destinatario = usuario.getEmail();
        String cuerpo = EmailUtils.generarTemplateCodigoValidacion(
                usuario.getNombre(),
                codigoActivacion,
                usuario.getEmail()
        );
        // Enviar correo
        emailServicio.enviarCorreo(new EmailDTO(asunto, cuerpo, destinatario));
    }


    @Override
    public void editar(String id,EditarUsuarioDTO editarUsuarioDTO) throws Exception {
        // Obtiene el ID del usuario autenticado desde el contexto de seguridad
        String idAutenticado = jwtUtilsHelper.obtenerIdUsuarioAutenticado();
        if (!idAutenticado.equals(id)) {
            throw new Exception ("No tienes permiso para editar este perfil.");
        }
        Usuario usuario = obtenerUsuarioPorId(id);
        usuarioMapper.editarUsuarioDTO(editarUsuarioDTO, usuario);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new Exception("ID inválido: " + id);
        }
        // Obtiene el ID del usuario autenticado desde el contexto de seguridad
        String idAutenticado = jwtUtilsHelper.obtenerIdUsuarioAutenticado();
        if (!idAutenticado.equals(id)) {
            throw new Exception ("No tienes permiso para eliminar este perfil.");
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

    //Metodo usado en activarCuenta y cambiarPassword para obtener el usuario por email
    private Usuario obtenerPorEmail(String email) throws RecursoNoEncontradoException {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(email);
        if (usuarioOptional.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontró el usuario con el email " + email);
        }
        return usuarioOptional.get();
    }

    private String generarCodigo() {
        String digitos = "0123456789";
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int indice = (int) (Math.random() * digitos.length());
            codigo.append(digitos.charAt(indice));
        }
        return codigo.toString();
    }


    @Override
    public void cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
        Usuario usuario = obtenerPorEmail(cambiarPasswordDTO.email());
        if (usuario.getCodigoValidacion() == null) {
            throw new IllegalArgumentException("El usuario no tiene un código de verificación");
        }
        if (!usuario.getCodigoValidacion().getCodigo().equals(cambiarPasswordDTO.codigoValidacion())) {
            throw new IllegalArgumentException("El código de verificación es incorrecto");
        }
        if (!LocalDateTime.now().isBefore(usuario.getCodigoValidacion().getFechaCreacion().plusMinutes(15))) {
            throw new IllegalArgumentException("El código de verificación ha caducado");
        }
        String nuevaPassword = passwordEncoder.encode(cambiarPasswordDTO.nuevaPassword());
        usuario.setPassword(nuevaPassword);
        usuario.setCodigoValidacion(null);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void activarCuenta(ActivarCuentaDTO activarCuentaDTO) throws Exception {
        Usuario usuario = obtenerPorEmail(activarCuentaDTO.email());
        if(!usuario.getCodigoValidacion().getCodigo().equals(activarCuentaDTO.codigoValidacion())) {
            throw new Exception("El código de verificación es incorrecto");
        }
        if(!LocalDateTime.now().isBefore(usuario.getCodigoValidacion().getFechaCreacion().plusMinutes(15))) {
            throw new Exception("El código de verificación ha caducado");
        }
        if (usuario.getCodigoValidacion() == null) {
            throw new Exception("No se encontró el usuario con el email ");
        }
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setCodigoValidacion(null);
        usuarioRepositorio.save(usuario);
    }



}
