package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
import co.edu.uniquindio.proyecto.dto.EditarReporteDTO;
import co.edu.uniquindio.proyecto.dto.ReporteDTO;

import java.util.List;

public interface ReporteServicio {
    
    String crear(CrearReporteDTO reporte) throws Exception;
    
    void editar(String id, EditarReporteDTO reporte) throws Exception;
    
    void eliminar(String id) throws Exception;
    
    ReporteDTO obtener(String id) throws Exception;
    
    List<ReporteDTO> listar(String titulo, String categoria, String estado, int pagina);
    
    void marcarImportante(String id) throws Exception;
    
    void cambiarEstado(String id, String nuevoEstado) throws Exception;
}
