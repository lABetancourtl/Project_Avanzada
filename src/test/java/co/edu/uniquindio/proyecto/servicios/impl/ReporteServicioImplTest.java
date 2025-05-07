package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
import co.edu.uniquindio.proyecto.dto.EditarReporteDTO;
import co.edu.uniquindio.proyecto.dto.ReporteDTO;
import co.edu.uniquindio.proyecto.dto.UbicacionDTO;
import co.edu.uniquindio.proyecto.mapper.ReporteMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.repositorios.ReporteRepositorio;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
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
        reporteServicio.actualizarReporte(reporteId, editarReporteDTO);
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
                reporteServicio.actualizarReporte(reporteId, editarReporteDTO)
        );
        assertEquals("El reporte no existe", exception.getMessage());
        // Verificar que no se haya llamado al mapper ni a guardar
        verify(reporteMapper, never()).EditarReporteDTO(any(), any());
        verify(reporteRepositorio, never()).save(any());
    }

    @Test
    void obtenerReporte_Exitoso() {
        // Arrange
        String id = new ObjectId().toHexString();
        ObjectId objectId = new ObjectId(id);

        // Mock de reporte retornado por la base de datos
        Reporte reporte = Mockito.mock(Reporte.class);
        Mockito.when(reporte.getId()).thenReturn(objectId);

        // Mock de DTO que retorna el mapper
        ReporteDTO reporteDTO = new ReporteDTO(
                id,
                "titulo",
                "Descripcion del reporte",
                "ABIERTO",
                LocalDateTime.now(),
                true,
                "644f1b5de3a5b768cbf4f3c9", // clienteId
                "Juan Pérez",
                new UbicacionDTO(123.12312, 2131.2312),
                "644f1b5de3a5b768cbf453c9", // categoriaId
                "Categoría Prueba",
                List.of("foto1.jpg", "foto2.jpg")
        );
        // Simulamos comportamiento del repositorio
        Mockito.when(reporteRepositorio.findById(objectId)).thenReturn(Optional.of(reporte));
        // Simulamos comportamiento del mapper
        Mockito.when(reporteMapper.toDTO(reporte)).thenReturn(reporteDTO);
        // Act
        ReporteDTO resultado = reporteServicio.obtenerMisReportes(id);
        // Assert
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(id, resultado.id());
        Assertions.assertEquals("titulo", resultado.titulo());
        Assertions.assertEquals("Descripcion del reporte", resultado.descripcion());
        Assertions.assertEquals("ABIERTO", resultado.estado());
        Assertions.assertTrue(resultado.importante());
        Assertions.assertEquals("644f1b5de3a5b768cbf4f3c9", resultado.clienteId());
        Assertions.assertEquals("Juan Pérez", resultado.nombreCliente());
        Assertions.assertEquals(new UbicacionDTO(123.12312, 2131.2312), resultado.ubicacion());
        Assertions.assertEquals("644f1b5de3a5b768cbf453c9", resultado.categoriaId());
        Assertions.assertEquals("Categoría Prueba", resultado.nombreCategoria());
        Assertions.assertEquals(List.of("foto1.jpg", "foto2.jpg"), resultado.fotos());
        // Verificamos las interacciones
        Mockito.verify(reporteRepositorio).findById(objectId);
        Mockito.verify(reporteMapper).toDTO(reporte);
    }
    @Test
    void obtenerReporte_IdInvalido_LanzaExcepcion() {
        // Arrange
        String idInvalido = "1234"; // Esto no es un ObjectId válido

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reporteServicio.obtenerMisReportes(idInvalido);
        });

        // Verificamos que no se llamen las dependencias, porque el método debe fallar antes
        Mockito.verifyNoInteractions(reporteRepositorio);
        Mockito.verifyNoInteractions(reporteMapper);
    }
    @Test
    void obtenerReporte_NoExiste_LanzaExcepcion() {
        // Arrange
        String idValido = new ObjectId().toHexString();
        ObjectId objectId = new ObjectId(idValido);

        // Simulamos que el repositorio no encuentra el reporte
        Mockito.when(reporteRepositorio.findById(objectId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            reporteServicio.obtenerMisReportes(idValido);
        });

        // Verificamos que se llamó al repositorio pero no al mapper
        Mockito.verify(reporteRepositorio).findById(objectId);
        Mockito.verifyNoInteractions(reporteMapper);
    }

}


