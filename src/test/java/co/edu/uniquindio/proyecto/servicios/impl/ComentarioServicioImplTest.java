package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.mapper.ComentarioMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Comentario;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepositorio;
import co.edu.uniquindio.proyecto.repositorios.ReporteRepositorio;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.servicios.EmailServicio;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComentarioServicioImplTest {

    @InjectMocks
    private ComentarioServicioImpl comentarioServicio;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private ReporteRepositorio reporteRepositorio;

    @Mock
    private ComentarioMapper comentarioMapper;

    @Mock
    private ComentarioRepositorio comentarioRepositorio;

    @Mock
    private EmailServicio emailServicio;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void crearComentarioExitosamente() throws Exception {
        // Arrange
        String idReporte = new ObjectId().toHexString();
        CrearComentarioDTO crearComentarioDTO = new CrearComentarioDTO(
                "Mensaje de prueba",
                new ObjectId().toHexString() // clienteId generado dinámicamente
        );

        Reporte reporte = new Reporte();
        reporte.setClienteId(new ObjectId());
        reporte.setTitulo("Reporte de prueba");

        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@prueba.com");
        usuario.setNombre("Usuario Prueba");

        Comentario comentario = new Comentario();
        comentario.setId(new ObjectId());
        comentario.setMensaje(crearComentarioDTO.mensaje());
        comentario.setFecha(LocalDateTime.now());

        when(reporteRepositorio.findById(new ObjectId(idReporte))).thenReturn(Optional.of(reporte));
        when(comentarioMapper.toDocument(crearComentarioDTO)).thenReturn(comentario);
        when(usuarioRepositorio.findById(reporte.getClienteId())).thenReturn(Optional.of(usuario));

        // Act
        comentarioServicio.crearComentario(idReporte, crearComentarioDTO);

        // Assert
        verify(comentarioRepositorio, times(1)).save(any(Comentario.class));
        verify(emailServicio, times(1)).enviarCorreo(any(EmailDTO.class));
    }

    @Test
    void editarComentarioExitosamente() throws Exception {
        // Arrange
        String idReporte = new ObjectId().toHexString();
        String idComentario = new ObjectId().toHexString();
        String nuevoMensaje = "Mensaje editado";

        Comentario comentario = new Comentario();
        ObjectId reporteId = new ObjectId(idReporte);
        ObjectId comentarioId = new ObjectId(idComentario);
        comentario.setReporteId(reporteId);
        comentario.setUsuarioId(new ObjectId("507f1f77bcf86cd799439011"));

        when(reporteRepositorio.findById(reporteId)).thenReturn(Optional.of(new Reporte()));
        when(comentarioRepositorio.findById(comentarioId)).thenReturn(Optional.of(comentario));
        when(authentication.getName()).thenReturn("507f1f77bcf86cd799439011");

        // Act
        comentarioServicio.editarComentario(idReporte, idComentario, nuevoMensaje);

        // Assert
        verify(comentarioRepositorio, times(1)).save(comentario);
        assertEquals(nuevoMensaje, comentario.getMensaje());
    }

    @Test
    void obtenerComentariosExitosamente() throws Exception {
        // Arrange
        String idReporte = new ObjectId().toHexString();
        ObjectId objectId = new ObjectId(idReporte);

        Comentario comentario = new Comentario();
        comentario.setId(new ObjectId());

        ComentarioDTO comentarioDTO = new ComentarioDTO(
                new ObjectId().toHexString(),          // id
                "Mensaje de prueba",                   // mensaje
                LocalDateTime.now(),                   // fecha
                new ObjectId().toHexString(),          // clienteId
                "Nombre del cliente de prueba",        // nombreCliente
                new ObjectId().toHexString()           // reporteId
        );

        when(reporteRepositorio.findById(objectId)).thenReturn(Optional.of(new Reporte()));
        when(comentarioRepositorio.findByReporteId(objectId)).thenReturn(List.of(comentario));
        when(comentarioMapper.toDTO(comentario)).thenReturn(comentarioDTO);

        // Act
        List<ComentarioDTO> comentarios = comentarioServicio.obtenerComentarios(idReporte);

        // Assert
        assertNotNull(comentarios);
        assertEquals(1, comentarios.size());
    }
}

