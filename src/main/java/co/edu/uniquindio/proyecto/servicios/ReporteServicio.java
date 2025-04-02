package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
import co.edu.uniquindio.proyecto.dto.EditarReporteDTO;
import co.edu.uniquindio.proyecto.dto.ReporteDTO;
import jakarta.validation.Valid;

public interface ReporteServicio {

    void crear(@Valid CrearReporteDTO reporte) throws Exception;

    void editar(String id, @Valid EditarReporteDTO reporte) throws Exception;

    void eliminar(String id) throws Exception;

    ReporteDTO obtener(String id) throws Exception;

    void marcarImportante(String id) throws Exception;

    void cambiarEstado(String id, String nuevoEstado) throws Exception;
}

