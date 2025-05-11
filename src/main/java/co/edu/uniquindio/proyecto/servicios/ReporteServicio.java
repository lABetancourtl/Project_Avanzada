package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.*;
import jakarta.validation.Valid;

import java.util.List;

public interface ReporteServicio {

    void crearReporte(@Valid CrearReporteDTO reporte) throws Exception;
    void eliminarReporte(String id) throws Exception;
    void actualizarReporte(String id, EditarReporteDTO reporte) throws Exception;
   // ReporteDTO obtener(String id) throws Exception;

    List<ReporteDTO> obtenerMisReportes();

    List<ReporteDTO> obtenerReportePorCategoria(String categoria);

    List<ReporteDTO> obtenerReportesPorRadio(double latitud, double longitud, double radio);

    void cambiarEstadoReporte(String id, CambiarEstadoDTO estado) throws Exception;


    void marcarReporteImportante(String id) throws Exception;

    List<ReporteDTO> obtenerReportesPorCiudad(String nombreCiudad);
}

