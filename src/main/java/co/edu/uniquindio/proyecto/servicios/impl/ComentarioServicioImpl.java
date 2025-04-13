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
import co.edu.uniquindio.proyecto.servicios.ComentarioServicio;
import co.edu.uniquindio.proyecto.servicios.EmailServicio;
import co.edu.uniquindio.proyecto.util.EmailTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServicioImpl implements ComentarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;

    private final ReporteRepositorio reporteRepositorio;

    private final ComentarioMapper comentarioMapper;

    private final ComentarioRepositorio comentarioRepositorio;

    private final EmailServicio emailServicio;

    @Override
    public void crearComentario(String idReporte, CrearComentarioDTO crearComentarioDTO) throws Exception {
        // Validar que el ID del reporte tenga formato correcto
        if (!ObjectId.isValid(idReporte)) {
            throw new IllegalArgumentException("El ID del reporte no es válido: " + idReporte);
        }
        // Validar que el mensaje no sea vacío o en blanco
        if (crearComentarioDTO.mensaje() == null || crearComentarioDTO.mensaje().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje del comentario no puede estar vacío");
        }
        ObjectId objectId = new ObjectId(idReporte);
        // Verificar que el reporte exista y obtenerlo
        Reporte reporte = reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));
        // Mapear CrearComentarioDTO a Comentario utilizando el mapper
        Comentario comentario = comentarioMapper.toDocument(crearComentarioDTO);
        // Generar un nuevo ID, establecer fecha actual y asignar el reporteId
        comentario.setId(new ObjectId());
        comentario.setFecha(LocalDateTime.now());
        comentario.setReporteId(objectId);
        // Guardar el comentario directamente en la colección de comentarios
        comentarioRepositorio.save(comentario);
        // Notificación por correo al creador del reporte
        Usuario usuario = usuarioRepositorio.findById(reporte.getClienteId())
                .orElseThrow(() -> new Exception("Usuario creador del reporte no encontrado"));
        String asunto = "Nuevo comentario en tu reporte";
        String destinatario = usuario.getEmail();
        String cuerpo = EmailTemplateUtil.generarTemplateNuevoComentario(
                usuario.getNombre(),
                reporte.getTitulo(),
                crearComentarioDTO.mensaje()
        );
        emailServicio.enviarCorreo(new EmailDTO(asunto, cuerpo, destinatario));
    }

    @Override
    public void editarComentario(String idReporte, String idComentario, String nuevoMensaje) throws Exception {
        // Validar IDs
        if (!ObjectId.isValid(idReporte) || !ObjectId.isValid(idComentario)) {
            throw new IllegalArgumentException("El ID del reporte o del comentario no es válido");
        }
        ObjectId reporteObjectId = new ObjectId(idReporte);
        ObjectId comentarioObjectId = new ObjectId(idComentario);
        // Verificar que el reporte exista
        reporteRepositorio.findById(reporteObjectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));
        // Buscar el comentario por ID
        Comentario comentario = comentarioRepositorio.findById(comentarioObjectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un comentario con el id: " + idComentario));
        // Verificar que el comentario pertenezca al reporte correcto
        if (!comentario.getReporteId().equals(reporteObjectId)) {
            throw new IllegalArgumentException("El comentario no pertenece al reporte indicado");
        }
        // Obtener usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usuarioAutenticadoId = authentication.getName();
        // Validar que el usuario autenticado sea el creador del comentario
        if (!comentario.getUsuarioId().toHexString().equals(usuarioAutenticadoId)) {
            throw new IllegalAccessException("No tienes permiso para editar este comentario");
        }
        // Actualizar mensaje
        comentario.setMensaje(nuevoMensaje);
        // Guardar cambios
        comentarioRepositorio.save(comentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentarios(String idReporte) throws Exception {
        // Validar que el ID del reporte tenga formato correcto
        if (!ObjectId.isValid(idReporte)) {
            throw new IllegalArgumentException("El ID del reporte no es válido: " + idReporte);
        }
        ObjectId objectId = new ObjectId(idReporte);
        // Verificar que el reporte exista
        reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));
        // Consultar los comentarios directamente en la colección de comentarios
        List<Comentario> comentarios = comentarioRepositorio.findByReporteId(objectId);
        // Mapear los comentarios a DTO y devolverlos
        return comentarios.stream()
                .map(comentarioMapper::toDTO)
                .collect(Collectors.toList());
    }

}
