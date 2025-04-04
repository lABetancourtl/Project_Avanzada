package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.mapper.UsuarioMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;

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
        ObjectId objectId = editarUsuarioDTO.id();
        Usuario usuario = usuarioRepositorio.findById(objectId)
                .orElseThrow(() -> new Exception("El usuario con ID " + editarUsuarioDTO.id() + " no existe."));
        usuarioMapper.editarUsuarioDTO(editarUsuarioDTO, usuario);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new Exception("ID inválido: " + id);
        }
        ObjectId objectId = new ObjectId(id);
        Usuario usuario = usuarioRepositorio.findById(objectId)
                .orElseThrow(() -> new Exception("El usuario con ID " + id + " no existe."));
        usuario.setEstado(EstadoUsuario.ELIMINADO);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public UsuarioDTO obtener(String id) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new Exception("ID inválido: " + id);
        }
        ObjectId objectId = new ObjectId(id);
        Usuario usuario = usuarioRepositorio.findById(objectId)
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

    private boolean existeEmail(String email) {
        return usuarioRepositorio.findByEmail(email).isPresent();
    }

}
