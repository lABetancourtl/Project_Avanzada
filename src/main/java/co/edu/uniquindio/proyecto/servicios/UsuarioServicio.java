package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CrearUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.EditarUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UsuarioServicio {
    
    String crear(CrearUsuarioDTO usuario) throws Exception;
    
    void editar(EditarUsuarioDTO usuario) throws Exception;
    
    void eliminar(String id) throws Exception;
    
    UsuarioDTO obtener(String id) throws Exception;
    
    List<UsuarioDTO> listarTodos(String nombre, String ciudad, int pagina);
}