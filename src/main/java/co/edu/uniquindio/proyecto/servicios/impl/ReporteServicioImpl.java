package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.modelo.documents.Categoria;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.vo.HistorialReporte;
import co.edu.uniquindio.proyecto.modelo.vo.Ubicacion;
import co.edu.uniquindio.proyecto.repositorios.ReporteRepositorio;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.seguridad.JWTUtilsHelper;
import co.edu.uniquindio.proyecto.servicios.EmailServicio;
import co.edu.uniquindio.proyecto.servicios.ImagenServicio;
import co.edu.uniquindio.proyecto.servicios.ReporteServicio;
import co.edu.uniquindio.proyecto.mapper.ReporteMapper;
import co.edu.uniquindio.proyecto.util.EmailTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final ImagenServicioImpl imagenServicio;
    private final EmailServicio emailServicio;
    private final JWTUtilsHelper jwtUtilsHelper;



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
        // 🛡️ Verificar que el usuario autenticado sea el propietario del reporte
        String idUsuarioAutenticado = jwtUtilsHelper.obtenerIdUsuarioAutenticado();
        if (!reporteExistente.getClienteId().toHexString().equals(idUsuarioAutenticado)) {
            throw new IllegalAccessException("No tienes permisos para actualizar este reporte");
        }
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
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("El ID proporcionado no es válido: " + id);
        }
        ObjectId objectId = new ObjectId(id);
        Reporte reporte = reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + id));
        // Obtener el ID del usuario autenticado
        String idUsuarioAutenticado = jwtUtilsHelper.obtenerIdUsuarioAutenticado();
        // Validar que el usuario autenticado sea el dueño del reporte
        if (!reporte.getClienteId().toString().equals(idUsuarioAutenticado)) {
            throw new SecurityException("No tienes permisos para eliminar este reporte");
        }
        reporte.setEstadoActual(EstadoReporte.ELIMINADO);
        reporteRepositorio.save(reporte);
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

    @Override
    public void cambiarEstadoReporte(String idReporte, CambiarEstadoDTO cambiarEstadoDTO) throws Exception {
        // Validar el ID del reporte
        if (!ObjectId.isValid(idReporte)) {
            throw new IllegalArgumentException("El ID del reporte no es válido: " + idReporte);
        }
        ObjectId objectId = new ObjectId(idReporte);
        // Buscar el reporte
        Reporte reporte = reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));
        // Actualizar el estado del reporte
        EstadoReporte nuevoEstado = EstadoReporte.valueOf(cambiarEstadoDTO.nuevoEstado());
        reporte.setEstadoActual(nuevoEstado);

        List<HistorialReporte> historial = reporte.getHistorial();
        if (historial == null) {
            historial = new ArrayList<>();
        }
        historial.add(HistorialReporte.builder()
                .estado(nuevoEstado)
                .observacion("Cambio de estado del reporte a: " + nuevoEstado.name())
                .fecha(LocalDateTime.now())
                .clienteId(reporte.getClienteId())
                .build());
        reporte.setHistorial(historial);
        // Guardar los cambios
        reporteRepositorio.save(reporte);
        // 📨 Notificar por correo al creador del reporte
        Usuario usuario = usuarioRepositorio.findById(reporte.getClienteId())
                .orElseThrow(() -> new NoSuchElementException("No se encontró el usuario del reporte"));
        String asunto = "Actualización del estado de tu reporte";
        String cuerpo = EmailTemplateUtil.generarTemplateCambioEstado(usuario.getNombre(), reporte.getTitulo(), nuevoEstado.name());
        String destinatario = usuario.getEmail();
        emailServicio.enviarCorreo(new EmailDTO(asunto, cuerpo, destinatario));
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
