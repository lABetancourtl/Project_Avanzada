package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CrearUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.EditarUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.UsuarioDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface UsuarioServicio {

    void crear(CrearUsuarioDTO cuenta);

    void editar(@Valid EditarUsuarioDTO cuenta);

    void eliminar(String id);

    UsuarioDTO obtener(String id);

    List<UsuarioDTO> listarTodos();
}
