package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ComentarioServicio {
    
    String crear(CrearComentarioDTO comentario) throws Exception;
    
    void eliminar(String id) throws Exception;
    
    List<ComentarioDTO> listarPorReporte(String reporteId);
}