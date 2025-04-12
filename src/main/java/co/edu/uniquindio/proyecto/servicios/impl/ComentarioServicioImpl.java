package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.mapper.ComentarioMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Comentario;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepositorio;
import co.edu.uniquindio.proyecto.repositorios.ReporteRepositorio;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.servicios.ComentarioServicio;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServicioImpl implements ComentarioServicio {

    private final ReporteRepositorio reporteRepositorio;

    private final ComentarioMapper comentarioMapper;

    private final ComentarioRepositorio comentarioRepositorio;

    @Override
    public void crearComentario(String idReporte, CrearComentarioDTO crearComentarioDTO) throws Exception {
        // Validar que el ID del reporte tenga formato correcto
        if (!ObjectId.isValid(idReporte)) {
            throw new IllegalArgumentException("El ID del reporte no es válido: " + idReporte);
        }
        ObjectId objectId = new ObjectId(idReporte);
        // Verificar que el reporte exista
        reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));
        // Mapear CrearComentarioDTO a Comentario utilizando el mapper
        Comentario comentario = comentarioMapper.toDocument(crearComentarioDTO);
        // Generar un nuevo ID, establecer fecha actual y asignar el reporteId
        comentario.setId(new ObjectId());
        comentario.setFecha(LocalDateTime.now());
        comentario.setReporteId(objectId);
        // Guardar el comentario directamente en la colección de comentarios
        comentarioRepositorio.save(comentario);
    }

    @Override
    public void editarComentario(String idReporte, String idComentario, String nuevoMensaje) throws Exception {
        // Validar los IDs
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
        // Actualizar el mensaje del comentario
        comentario.setMensaje(nuevoMensaje);
        // Guardar el comentario actualizado en la base de datos
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
