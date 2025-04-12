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
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public void actualizarReporte(String id, EditarReporteDTO dto) throws Exception {
        // Validar que el ID tenga un formato correcto
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("El ID proporcionado no es válido: " + id);
        }
        ObjectId objectId = new ObjectId(id);
        // Buscar el reporte existente
        Reporte reporteExistente = reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + id));
        // Utilizar el mapper para actualizar el documento existente
        reporteMapper.EditarReporteDTO(dto, reporteExistente);
        // Guardar los cambios
        reporteRepositorio.save(reporteExistente);
        // Notificar por WebSocket
        NotificacionDTO notificacionDTO = new NotificacionDTO(
                "Reporte Actualizado",
                "Se ha actualizado el reporte: " + reporteExistente.getTitulo(),
                "reports"
        );
        webSocketNotificationService.notificarClientes(notificacionDTO);
    }

    @Override
    public void eliminarReporte(String id) {
        // Validar el ID
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("El ID proporcionado no es válido: " + id);
        }
        // Buscar el reporte por ID
        ObjectId objectId = new ObjectId(id);
        Optional<Reporte> optionalReporte = reporteRepositorio.findById(objectId);
        if (optionalReporte.isEmpty()) {
            throw new NoSuchElementException("No se encontró un reporte con el id: " + id);
        }
        Reporte reporte = optionalReporte.get();
        reporte.setEstadoActual(EstadoReporte.ELIMINADO);

        reporteRepositorio.save(reporte);
        // Notificar por WebSocket que se eliminó un reporte (opcional, pero recomendable)
        NotificacionDTO notificacionDTO = new NotificacionDTO(
                "Reporte Eliminado",
                "Se ha eliminado el reporte: " + reporte.getTitulo(),
                "reports"
        );
        webSocketNotificationService.notificarClientes(notificacionDTO);
    }

    @Override
    public ReporteDTO obtener(String id) {
        // Validar que el ID tenga formato correcto
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("El ID proporcionado no es válido: " + id);
        }
        ObjectId objectId = new ObjectId(id);
        // Buscar el reporte por ID
        Reporte reporte = reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + id));
        // Convertir el documento a DTO usando el mapper
        return reporteMapper.toDTO(reporte);
    }

    @Override
    public void marcarImportante(String id) {

    }
    //Pilas falta esto
    @Override
    public void cambiarEstadoReporte(String id, CambiarEstadoDTO cambiarEstadoDTO) {
    //Lo mismo con comentartio que notifique via email
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
