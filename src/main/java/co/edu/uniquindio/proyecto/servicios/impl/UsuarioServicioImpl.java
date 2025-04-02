package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.mapper.UsuarioMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepositorio usuarioRepo;
    private final UsuarioMapper usuarioMapper; // <-- inyectado como @Component

    @Override
    public void crear(CrearUsuarioDTO dto) throws Exception {
        if (usuarioRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new Exception("El correo ya está registrado");
        }
        Usuario usuario = usuarioMapper.toDocument(dto);
        usuario.setRol(Rol.CLIENTE);
        usuario.setEstado(EstadoUsuario.INACTIVO);
        usuarioRepo.save(usuario);
    }

    @Override
    public void editar(EditarUsuarioDTO cuenta) throws Exception {
        ObjectId objectId = new ObjectId(cuenta.getId());
        Usuario usuario = usuarioRepo.findById(objectId)
                .orElseThrow(() -> new Exception("El usuario con ID " + cuenta.getId() + " no existe."));
        usuarioMapper.EditarUsuarioDTO(cuenta, usuario);
        usuarioRepo.save(usuario);
    }

    @Override
    public void eliminar(String id) throws Exception {
        Usuario usuario = usuarioRepo.findById(new ObjectId(id))
                .orElseThrow(() -> new Exception("El usuario con ID " + id + " no existe."));
        // Cambio lógico de estado
        usuario.setEstado(EstadoUsuario.ELIMINADO);
        usuarioRepo.save(usuario);
    }
//Pregunta es mejor asi ???, o mejor uno y no
    @Override
    public UsuarioDTO obtener(String identificador) throws Exception {
        Usuario usuario;
        // Verifica si es un ObjectId válido (24 caracteres hexadecimales)
        if (ObjectId.isValid(identificador)) {
            usuario = usuarioRepo.findById(new ObjectId(identificador))
                    .orElseThrow(() -> new Exception("El usuario con ID " + identificador + " no existe."));
        } else {
            // Si no es ID, intenta buscar por correo
            usuario = usuarioRepo.findByEmail(identificador)
                    .orElseThrow(() -> new Exception("No existe un usuario con el correo " + identificador));
        }
        if (usuario.getEstado() == EstadoUsuario.ELIMINADO) {
            throw new Exception("El usuario ha sido eliminado.");
        }
        return usuarioMapper.toDTO(usuario);
    }
//¿Buscar por todos??? O solo por email
    @Override
    public List<UsuarioDTO> listarTodos(String nombre, String ciudad, int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10); // 10 usuarios por página
        List<Usuario> resultados;
        if (nombre != null && ciudad != null) {
            resultados = usuarioRepo.findByNombreContainingIgnoreCaseAndCiudadContainingIgnoreCaseAndEstado(
                    nombre, ciudad, EstadoUsuario.ACTIVO, pageable);
        } else if (nombre != null) {
            resultados = usuarioRepo.findByNombreContainingIgnoreCaseAndEstado(nombre, EstadoUsuario.ACTIVO, pageable);
        } else if (ciudad != null) {
            resultados = usuarioRepo.findByCiudadContainingIgnoreCaseAndEstado(ciudad, EstadoUsuario.ACTIVO, pageable);
        } else {
            resultados = usuarioRepo.findByEstado(EstadoUsuario.ACTIVO, pageable);
        }
        return resultados.stream()
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

//    @Override
//    public List<UsuarioDTO> listarTodos(String nombre, String ciudad, int pagina) {
//        return List.of();
//    }
//
//    @Override
//    public TokenDTO login(LoginDTO loginDTO) throws Exception {
//        return null;
//    }
//    private final UsuarioMapper usuarioMapper;
////
//
//

//
//    @Override
//    public void eliminar(String id) throws Exception {
//        ObjectId objectId = new ObjectId(id);
//        Usuario usuario = usuarioRepo.findById(objectId)
//                .orElseThrow(() -> new Exception("El usuario con ID " + id + " no existe."));
//        // Validar si ya está eliminado
//        if (usuario.getEstado() == EstadoUsuario.ELIMINADO) {
//            throw new Exception("El usuario con ID " + id + " ya ha sido eliminado anteriormente.");
//        }
//        // Cambiar el estado a ELIMINADO
//        usuario.setEstado(EstadoUsuario.ELIMINADO);
//        // Guardar cambios
//        usuarioRepo.save(usuario);
//    }
//
//    @Override
//    public UsuarioDTO obtener(String id) throws Exception {
//        ObjectId objectId = new ObjectId(id);
//        Usuario usuario = usuarioRepo.findById(objectId)
//                .orElseThrow(() -> new Exception("El usuario con ID " + id + " no existe."));
//        return usuarioMapper.toDTO(usuario);
//    }
//
////    @Override
////    public void enviarCodigoVerificacion(String email) throws Exception {
////        Usuario usuario = usuarioRepo.findByEmail(email)
////                .orElseThrow(() -> new Exception("No existe un usuario registrado con el email: " + email));
////        // Generar código aleatorio de 6 dígitos
////        String codigo = String.valueOf((int)(Math.random() * 900000) + 100000);
////        // Crear el objeto de validación con expiración de 15 minutos
////        CodigoValidacion codigoValidacion = CodigoValidacion.builder()
////                .codigo(codigo)
////                .fechaCreacion(LocalDateTime.now().plusMinutes(15))
////                .build();
////        // Asignar al usuario
////        usuario.setCodigoValidacion(codigoValidacion);
////        // Guardar cambios
////        usuarioRepo.save(usuario);
////        // Simular envío por consola (luego se reemplaza por correo real)
////        System.out.println("Código de verificación enviado a " + email + ": " + codigo);
////    }
//    @Override
//    public void enviarCodigoVerificacion(String email) throws Exception {
//        Usuario usuario = usuarioRepo.findByEmail(email)
//                .orElseThrow(() -> new Exception("No existe un usuario registrado con el email: " + email));
//        // Generar código aleatorio
//        String codigo = String.valueOf((int)(Math.random() * 900000) + 100000);
//        // Crear código sin expiración aún
//        CodigoValidacion codigoValidacion = CodigoValidacion.builder()
//                .codigo(codigo)
//                .build();
//        usuario.setCodigoValidacion(codigoValidacion);
//        usuarioRepo.save(usuario);
//        System.out.println("Código de verificación enviado a " + email + ": " + codigo);
//    }
//
//    @Override
//    public void cambiarPassword(String email, CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
//
//    }
//
//    @Override
//    public void activarCuenta(String email, ActivarCuentaDTO activarCuentaDTO) throws Exception {
//
//    }
//
//    @Override
//    public void obtenerReportesUsuario(String id) {
//
//    }
    //----------------------------------------------------------------------
    //De aqui pa abajo no los tiene el profe
//    @Override
//    public List<UsuarioDTO> listarTodos(String nombre, String ciudad, int pagina) {
//        return null;
//    }
//
//    @Override
//    public boolean validarEmail(String email) {
//        return usuarioRepo.findByEmail(email).isPresent();
//    }
//
//    @Override
//    public TokenDTO login(LoginDTO loginDTO) throws Exception {
//        return null;
//    }
}
