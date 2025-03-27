package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.NotificacionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface NotificacionServicio {
    
    List<NotificacionDTO> listarPorUsuario(String usuarioId);
    
    void marcarLeida(String id) throws Exception;
    
    void marcarTodasLeidas(String usuarioId);
}