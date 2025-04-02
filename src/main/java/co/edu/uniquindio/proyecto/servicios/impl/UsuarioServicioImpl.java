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

    @Override
    public UsuarioDTO obtener(String id) throws Exception {
        Usuario usuario = usuarioRepo.findById(new ObjectId(id))
                .orElseThrow(() -> new Exception("El usuario con ID " + id + " no existe."));
        return usuarioMapper.toDTO(usuario);
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
}
