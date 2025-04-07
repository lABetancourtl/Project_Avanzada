package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.modelo.documents.Categoria;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.vo.Ubicacion;
import co.edu.uniquindio.proyecto.repositorios.ReporteRepositorio;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.servicios.ReporteServicio;
import co.edu.uniquindio.proyecto.mapper.ReporteMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServicioImpl implements ReporteServicio {


    private final ReporteRepositorio reporteRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ReporteMapper reporteMapper;
    private final WebSocketNotificationService webSocketNotificationService;

    @Override
    public void crearReporte(CrearReporteDTO dto) {
        // Buscar usuario o lanzar excepción personalizada
        Usuario usuario = obtenerUsuarioActivo(dto.clienteId());

        // Crear el documento Reporte a partir del DTO
        Reporte reporte = reporteMapper.toDocument(dto);
        asignarDatosAdicionales(reporte);

        // Guardar el reporte en la base de datos
        reporteRepositorio.save(reporte);

        // Enviar notificación por WebSocket
        notificarNuevoReporte(reporte);
    }


    @Override
    public void editarReporte(String id, EditarReporteDTO dto) throws Exception {
        // Buscar el reporte existente
        Reporte reporte = reporteRepositorio.findById(new ObjectId(id))
                .orElseThrow(() -> new Exception("El reporte no existe"));
        // Actualizar campos editables
        reporteMapper.EditarReporteDTO(dto, reporte);
        // Guardamos los cambios
        reporteRepositorio.save(reporte);
    }

    @Override
    public void eliminarReporte(String id) {

    }

    @Override
    public void actualizarReporte(String id, EditarReporteDTO reporte) throws Exception {

    }

    @Override
    public ReporteDTO obtener(String id) {
        return null;
    }

    @Override
    public void marcarImportante(String id) {

    }

    @Override
    public void cambiarEstadoReporte(String id, CambiarEstadoDTO cambiarEstadoDTO) {

    }

    @Override
    public InfoReporteDTO obtenerReporte(String id) throws Exception {
        return null;
    }

    @Override
    public List<InfoReporteDTO> obtenerReportes(String categoria, EstadoReporte estadoReporte, int pagina) throws Exception {
        return List.of();
    }

    @Override
    public List<InfoReporteDTO> obtenerReportesUsuario(String idusuario, int pagina) throws Exception {
        return List.of();
    }

    @Override
    public List<InfoReporteDTO> obtenerReportes(Ubicacion ubicacion) throws Exception {
        return List.of();
    }

    @Override
    public List<HistorialEstadoDTO> listarHistorialEstados(String id) throws Exception {
        return List.of();
    }

    private Usuario obtenerUsuarioActivo(String clienteId) {
        return usuarioRepositorio.findById(new ObjectId(clienteId))
                .filter(usuario -> usuario.getEstado() != EstadoUsuario.ELIMINADO)
                .orElseThrow(() -> new RuntimeException("El usuario no existe o está inactivo"));
    }

    private void asignarDatosAdicionales(Reporte reporte) {
        reporte.setEstadoActual(EstadoReporte.PENDIENTE);
        reporte.setFecha(LocalDateTime.now());
        reporte.setContadorImportante(0);
    }

    private void notificarNuevoReporte(Reporte reporte) {
        NotificacionDTO notificacionDTO = new NotificacionDTO(
                "Nuevo Reporte",
                "Se acaba de crear un nuevo reporte: " + reporte.getTitulo(),
                "reports"
        );
        webSocketNotificationService.notificarClientes(notificacionDTO);
    }

}
