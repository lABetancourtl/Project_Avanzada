package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
import co.edu.uniquindio.proyecto.dto.EditarReporteDTO;
import co.edu.uniquindio.proyecto.dto.ReporteDTO;
import jakarta.validation.Valid;

public interface ReporteServicio {


    void crear(@Valid CrearReporteDTO reporte);

    void editar(String id, @Valid EditarReporteDTO reporte);

    void eliminar(String id);

    ReporteDTO obtener(String id);

    void marcarImportante(String id);

    void cambiarEstado(String id, String nuevoEstado);
}
