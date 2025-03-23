package co.edu.uniquindio.proyecto.servicios.impl;


import co.edu.uniquindio.proyecto.dto.CrearUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.EditarUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.UsuarioDTO;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class UsuarioServicioImpl implements UsuarioServicio {

   private final List<UsuarioDTO> usuarios = new ArrayList<>();

    public UsuarioServicioImpl() {
        
    }

    @Override
    public void crear(CrearUsuarioDTO cuenta) {

    }

    @Override
    public void editar(EditarUsuarioDTO cuenta) {

    }

    @Override
    public void eliminar(String id) {

    }

    @Override
    public UsuarioDTO obtener(String id) {
        return null;
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        return usuarios;
    }

    @Override
    public List<UsuarioDTO> listarTodos(String nombre, String ciudad, int pagina) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarTodos'");
    }
}
