package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.UbicacionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UbicacionServicio {
    
    List<UbicacionDTO> obtenerCercanas(double latitud, double longitud, double radioKm);
}