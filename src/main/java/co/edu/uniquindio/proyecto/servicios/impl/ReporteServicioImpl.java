package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
import co.edu.uniquindio.proyecto.dto.EditarReporteDTO;
import co.edu.uniquindio.proyecto.dto.ReporteDTO;
import co.edu.uniquindio.proyecto.servicios.ReporteServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReporteServicioImpl implements ReporteServicio {
    @Override
    public void crear(CrearReporteDTO reporte) {

    }

    @Override
    public void editar(String id, EditarReporteDTO reporte) {

    }

    @Override
    public void eliminar(String id) {

    }

    @Override
    public ReporteDTO obtener(String id) {
        return null;
    }

    @Override
    public void marcarImportante(String id) {

    }

    @Override
    public void cambiarEstado(String id, String nuevoEstado) {

    }
}
