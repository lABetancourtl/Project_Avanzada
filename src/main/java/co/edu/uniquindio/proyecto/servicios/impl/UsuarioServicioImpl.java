package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;
import co.edu.uniquindio.proyecto.modelo.vo.CodigoValidacion;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import co.edu.uniquindio.proyecto.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsCogitnstructor
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepositorio usuarioRepo;
    private final UsuarioMapper usuarioMapper;

    @Override
    public void crear(CrearUsuarioDTO crearUsuarioDTO) throws Exception {
        // Validar si el email ya está en uso
        if (usuarioRepo.findByEmail(crearUsuarioDTO.getEmail()).isPresent()) {
            throw new Exception("El correo " + crearUsuarioDTO.getEmail() + " ya está registrado.");
        }
        // Mapear el DTO al documento Usuario
        Usuario usuario = usuarioMapper.toDocument(crearUsuarioDTO);
        // Asignar valores adicionales obligatorios
        usuario.setRol(Rol.CLIENTE);
        usuario.setEstado(EstadoUsuario.INACTIVO);
        // Guardar en base de datos
        usuarioRepo.save(usuario);
    }

    @Override
    public void editar(EditarUsuarioDTO cuenta) throws Exception {
        ObjectId objectId = new ObjectId(cuenta.getId());
        Usuario usuario = usuarioRepo.findById(objectId)
                .orElseThrow(() -> new Exception("El usuario con ID " + cuenta.getId() + " no existe."));
        // Actualizar campos permitidos
        usuario.setNombre(cuenta.getNombre());
        usuario.setTelefono(cuenta.getTelefono());
        usuario.setDireccion(cuenta.getDireccion());
        usuario.setCiudad(cuenta.getCiudad());
        usuarioRepo.save(usuario);
    }

    @Override
    public void eliminar(String id) throws Exception {
        ObjectId objectId = new ObjectId(id);
        Usuario usuario = usuarioRepo.findById(objectId)
                .orElseThrow(() -> new Exception("El usuario con ID " + id + " no existe."));
        // Validar si ya está eliminado
        if (usuario.getEstado() == EstadoUsuario.ELIMINADO) {
            throw new Exception("El usuario con ID " + id + " ya ha sido eliminado anteriormente.");
        }
        // Cambiar el estado a ELIMINADO
        usuario.setEstado(EstadoUsuario.ELIMINADO);
        // Guardar cambios
        usuarioRepo.save(usuario);
    }

    @Override
    public UsuarioDTO obtener(String id) throws Exception {
        ObjectId objectId = new ObjectId(id);
        Usuario usuario = usuarioRepo.findById(objectId)
                .orElseThrow(() -> new Exception("El usuario con ID " + id + " no existe."));
        return usuarioMapper.toDTO(usuario);
    }

//    @Override
//    public void enviarCodigoVerificacion(String email) throws Exception {
//        Usuario usuario = usuarioRepo.findByEmail(email)
//                .orElseThrow(() -> new Exception("No existe un usuario registrado con el email: " + email));
//        // Generar código aleatorio de 6 dígitos
//        String codigo = String.valueOf((int)(Math.random() * 900000) + 100000);
//        // Crear el objeto de validación con expiración de 15 minutos
//        CodigoValidacion codigoValidacion = CodigoValidacion.builder()
//                .codigo(codigo)
//                .fechaCreacion(LocalDateTime.now().plusMinutes(15))
//                .build();
//        // Asignar al usuario
//        usuario.setCodigoValidacion(codigoValidacion);
//        // Guardar cambios
//        usuarioRepo.save(usuario);
//        // Simular envío por consola (luego se reemplaza por correo real)
//        System.out.println("Código de verificación enviado a " + email + ": " + codigo);
//    }
    @Override
    public void enviarCodigoVerificacion(String email) throws Exception {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new Exception("No existe un usuario registrado con el email: " + email));
        // Generar código aleatorio
        String codigo = String.valueOf((int)(Math.random() * 900000) + 100000);
        // Crear código sin expiración aún
        CodigoValidacion codigoValidacion = CodigoValidacion.builder()
                .codigo(codigo)
                .build();
        usuario.setCodigoValidacion(codigoValidacion);
        usuarioRepo.save(usuario);
        System.out.println("Código de verificación enviado a " + email + ": " + codigo);
    }

    @Override
    public void cambiarPassword(String email, CambiarPasswordDTO cambiarPasswordDTO) throws Exception {

    }

    @Override
    public void activarCuenta(String email, ActivarCuentaDTO activarCuentaDTO) throws Exception {

    }

    @Override
    public void obtenerReportesUsuario(String id) {

    }
    //----------------------------------------------------------------------
    //De aqui pa abajo no los tiene el profe
    @Override
    public List<UsuarioDTO> listarTodos(String nombre, String ciudad, int pagina) {
        return null;
    }

    @Override
    public boolean validarEmail(String email) {
        return usuarioRepo.findByEmail(email).isPresent();
    }

    @Override
    public TokenDTO login(LoginDTO loginDTO) throws Exception {
        return null;
    }
}
