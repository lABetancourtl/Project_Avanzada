package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.UbicacionDTO;

import java.util.List;

public interface UbicacionServicio {
    
    List<UbicacionDTO> obtenerCercanas(double latitud, double longitud, double radioKm);
}