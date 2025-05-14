package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.vo.HistorialReporte;
import co.edu.uniquindio.proyecto.repositorios.ReporteRepositorio;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.seguridad.JWTUtilsHelper;
import co.edu.uniquindio.proyecto.servicios.EmailServicio;
import co.edu.uniquindio.proyecto.servicios.ReporteServicio;
import co.edu.uniquindio.proyecto.mapper.ReporteMapper;
import co.edu.uniquindio.proyecto.servicios.impl.mapbox.MapboxService;
import co.edu.uniquindio.proyecto.util.EmailTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteServicioImpl implements ReporteServicio {


    private final ReporteRepositorio reporteRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ReporteMapper reporteMapper;
    private final WebSocketNotificationService webSocketNotificationService;
    private final EmailServicio emailServicio;
    private final JWTUtilsHelper jwtUtilsHelper;
    private final MapboxService mapboxService;



    @Override
    public void crearReporte(CrearReporteDTO dto) {
        // Obtener el ID del usuario autenticado desde el token JWT
        String idCliente = jwtUtilsHelper.obtenerIdUsuarioAutenticado();

        // Buscar usuario o lanzar excepción personalizada
        Usuario usuario = obtenerUsuarioActivo(idCliente);

        // Crear el documento Reporte a partir del DTO
        Reporte reporte = reporteMapper.toDocument(dto);

        // Asignar manualmente el cliente al reporte
        reporte.setClienteId(usuario.getId());

        // Asignar la ciudad automáticamente con las coordenadas (latitud, longitud)
        String ciudad = mapboxService.obtenerCiudad(reporte.getUbicacion().getLatitud(), reporte.getUbicacion().getLongitud());
        if (ciudad != null) {
            reporte.setCiudad(ciudad);  // Asignamos la ciudad al reporte
        } else {
            // En caso de que no se encuentre la ciudad, podrías manejar el error aquí
            reporte.setCiudad("Ciudad desconocida");
        }

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
        // Verificar que el usuario autenticado sea el propietario del reporte
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

/*
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
 */

    @Override
    public List<ReporteDTO> obtenerMisReportes() {
        String idUsuario = jwtUtilsHelper.obtenerIdUsuarioAutenticado();

        ObjectId idCliente = new ObjectId(idUsuario);
        List<Reporte> reportes = reporteRepositorio.findAllByClienteId(idCliente);

        // Convertir la lista de entidades a DTOs
        return reportes.stream()
                .map(reporteMapper::toDTO)
                .toList();
    }


    @Override
    public List<ReporteDTO> obtenerReportePorCategoria(String categoriaId) {
        // Convertimos el String a ObjectId
        ObjectId categoriaObjectId = new ObjectId(categoriaId);
        List<Reporte> reportes = reporteRepositorio.findByCategoriaId(categoriaObjectId);
        return reportes.stream()
                .map(reporteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReporteDTO> obtenerReportesPorRadio(double latitud, double longitud, double radioKm) {
        List<Reporte> todosLosReportes = reporteRepositorio.findAll();

        return todosLosReportes.stream()
                .filter(reporte -> {
                    double distancia = calcularDistanciaHaversine(
                            latitud, longitud,
                            reporte.getUbicacion().getLatitud(), reporte.getUbicacion().getLongitud()
                    );
                    return distancia <= radioKm;
                })
                .map(reporteMapper::toDTO)
                .collect(Collectors.toList());
    }

    private double calcularDistanciaHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int RADIO_TIERRA_KM = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA_KM * c;
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

    @Override
    public void marcarReporteImportante(String id) {

    }


    @Override
    public List<ReporteDTO> obtenerReportesPorCiudad(String nombreCiudad) {
        List<ReporteDTO> reportes = reporteRepositorio.obtenerReportes(nombreCiudad);
        return reportes;
    }



}
