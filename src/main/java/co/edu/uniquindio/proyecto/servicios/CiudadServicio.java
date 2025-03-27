package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CiudadDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CiudadServicio {
    
    List<CiudadDTO> listar();
}