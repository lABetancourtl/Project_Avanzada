package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.EstadisticaDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EstadisticasServicio {
    
    List<EstadisticaDTO> obtenerPorCategoria(LocalDate fechaInicio, LocalDate fechaFin);
    
    List<EstadisticaDTO> obtenerPorCiudad(LocalDate fechaInicio, LocalDate fechaFin);
    
    Map<String, Object> obtenerResumen();
}