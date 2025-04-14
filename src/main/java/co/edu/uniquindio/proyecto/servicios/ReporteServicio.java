package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;
import co.edu.uniquindio.proyecto.modelo.vo.Ubicacion;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReporteServicio {

    void crearReporte(@Valid CrearReporteDTO reporte) throws Exception;
    void eliminarReporte(String id) throws Exception;
    void actualizarReporte(String id, EditarReporteDTO reporte) throws Exception;
    ReporteDTO obtener(String id) throws Exception;

    List<ReporteDTO> obtenerPorCategoria(String categoria);

    void marcarImportante(String id) throws Exception;
    void cambiarEstadoReporte(String id, CambiarEstadoDTO estado) throws Exception;
    InfoReporteDTO obtenerReporte(String id) throws Exception;
    List<InfoReporteDTO> obtenerReportes(String categoria, EstadoReporte estadoReporte, int pagina) throws Exception;
    List<InfoReporteDTO> obtenerReportesUsuario(String idusuario, int pagina) throws Exception;
    List<InfoReporteDTO> obtenerReportes(Ubicacion ubicacion) throws Exception;
    List<HistorialEstadoDTO> listarHistorialEstados(String id) throws Exception;

    //void crearReporte(CrearReporteDTO reporte, MultipartFile imagen) throws Exception;
}

