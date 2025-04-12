package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;

import java.util.List;

public interface ComentarioServicio {
    List<ComentarioDTO> obtenerComentarios(String idReporte) throws Exception;
    void crearComentario(String idReporte, CrearComentarioDTO comentarioDTO) throws Exception;
    void editarComentario(String idReporte, String idComentario, String nuevoMensaje) throws Exception;
}
