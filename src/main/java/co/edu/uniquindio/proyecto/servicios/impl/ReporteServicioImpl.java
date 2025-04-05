package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
import co.edu.uniquindio.proyecto.dto.EditarReporteDTO;
import co.edu.uniquindio.proyecto.dto.NotificacionDTO;
import co.edu.uniquindio.proyecto.dto.ReporteDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Categoria;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.repositorios.ReporteRepositorio;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.servicios.ReporteServicio;
import co.edu.uniquindio.proyecto.mapper.ReporteMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReporteServicioImpl implements ReporteServicio {


    private final ReporteRepositorio reporteRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ReporteMapper reporteMapper;
    private final WebSocketNotificationService webSocketNotificationService;

    @Override
    public void crear(CrearReporteDTO dto) {
        Usuario usuario = obtenerUsuarioActivo(dto.clienteId()); // Buscar usuario o lanzar excepción personalizada

        Reporte reporte = reporteMapper.toDocument(dto);         // Crear el documento Reporte a partir del DTO
        asignarDatosAdicionales(reporte);

        reporteRepositorio.save(reporte);                        // Guardar el reporte en la base de datos

        notificarNuevoReporte(reporte);                          // Enviar notificación por WebSocket
    }


    @Override
    public void editar(String id, EditarReporteDTO dto) throws Exception {
        // Buscar el reporte existente
        Reporte reporte = reporteRepositorio.findById(new ObjectId(id))
                .orElseThrow(() -> new Exception("El reporte no existe"));
        // Actualizar campos editables
        reporteMapper.EditarReporteDTO(dto, reporte);
        // Guardamos los cambios
        reporteRepositorio.save(reporte);
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
