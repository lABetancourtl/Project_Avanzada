package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
import co.edu.uniquindio.proyecto.dto.EditarReporteDTO;
import co.edu.uniquindio.proyecto.dto.UbicacionDTO;
import co.edu.uniquindio.proyecto.mapper.ReporteMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.repositorios.ReporteRepositorio;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ReporteServicioImplTest {

    @InjectMocks
    private ReporteServicioImpl reporteServicio;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private ReporteRepositorio reporteRepositorio;

    @Mock
    private ReporteMapper reporteMapper;

    @Mock
    private WebSocketNotificationService webSocketNotificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearReporteExitosamente() {
        // Arrange
        CrearReporteDTO crearReporteDTO = new CrearReporteDTO(
                "Título del reporte",
                "Descripción del reporte",
                "644f1b5de3a5b768cbf4f3c9",
                new UbicacionDTO(123.456, 78.910),
                "categoria123",
                true,
                List.of("foto1.jpg", "foto2.jpg")
        );
        Usuario usuario = new Usuario();
        Reporte reporte = new Reporte();
        ObjectId clienteId = new ObjectId(crearReporteDTO.clienteId());
        when(usuarioRepositorio.findById(clienteId))
                .thenReturn(Optional.of(usuario));
        when(reporteMapper.toDocument(crearReporteDTO)).thenReturn(reporte);
        // Act
        reporteServicio.crearReporte(crearReporteDTO);
        // Assert
        verify(usuarioRepositorio).findById(clienteId);
        verify(reporteMapper).toDocument(crearReporteDTO);
        verify(reporteRepositorio).save(reporte);
        verify(webSocketNotificationService).notificarClientes(any());
    }
    @Test
    void crearReporteConUsuarioNoExistenteDebeLanzarExcepcion() {
        // Arrange
        CrearReporteDTO crearReporteDTO = new CrearReporteDTO(
                "Título del reporte",
                "Descripción del reporte",
                "644f1b5de3a5b768cbf4f3c9", // ID de cliente válido formato ObjectId
                new UbicacionDTO(123.456, 78.910),
                "categoria123",
                true,
                List.of("foto1.jpg", "foto2.jpg")
        );
        ObjectId clienteId = new ObjectId(crearReporteDTO.clienteId());
        // Mock: usuario no encontrado
        when(usuarioRepositorio.findById(clienteId))
                .thenReturn(Optional.empty());
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                reporteServicio.crearReporte(crearReporteDTO)
        );
        assertEquals("El usuario no existe o está inactivo", exception.getMessage());
        // Verificar que no se llamó al mapper ni al repositorio ni a la notificación
        verify(reporteMapper, never()).toDocument(any());
        verify(reporteRepositorio, never()).save(any());
        verify(webSocketNotificationService, never()).notificarClientes(any());
    }
    //Test para editar
    @Test
    void editarReporteExitosamente() throws Exception {
        // Arrange
        String reporteId = "644f1b5de3a5b768cbf4f3c9";
        ObjectId objectId = new ObjectId(reporteId);

        EditarReporteDTO editarReporteDTO = new EditarReporteDTO(
                "Nuevo título",
                "Nueva descripción",
                new UbicacionDTO(123.456, 78.910),
                "categoria123",
                true,
                List.of("foto1.jpg", "foto2.jpg")
        );
        Reporte reporteExistente = new Reporte();
        // Mock: reporte existente
        when(reporteRepositorio.findById(objectId))
                .thenReturn(Optional.of(reporteExistente));
        // Act
        reporteServicio.editarReporte(reporteId, editarReporteDTO);
        // Assert
        verify(reporteRepositorio).findById(objectId);
        verify(reporteMapper).EditarReporteDTO(editarReporteDTO, reporteExistente);
        verify(reporteRepositorio).save(reporteExistente);
    }
    @Test
    void editarReporteConReporteNoExistenteDebeLanzarExcepcion() {
        // Arrange
        String reporteId = "644f1b5de3a5b768cbf4f3c9";
        ObjectId objectId = new ObjectId(reporteId);
        EditarReporteDTO editarReporteDTO = new EditarReporteDTO(
                "Nuevo título",
                "Nueva descripción",
                new UbicacionDTO(123.456, 78.910),
                "categoria123",
                true,
                List.of("foto1.jpg", "foto2.jpg")
        );
        // Mock: reporte no encontrado
        when(reporteRepositorio.findById(objectId))
                .thenReturn(Optional.empty());
        // Act & Assert
        Exception exception = assertThrows(Exception.class, () ->
                reporteServicio.editarReporte(reporteId, editarReporteDTO)
        );
        assertEquals("El reporte no existe", exception.getMessage());
        // Verificar que no se haya llamado al mapper ni a guardar
        verify(reporteMapper, never()).EditarReporteDTO(any(), any());
        verify(reporteRepositorio, never()).save(any());
    }

}


