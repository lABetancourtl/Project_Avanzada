package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.mapper.ComentarioMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Comentario;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
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

    @Override
    public void crearComentario(String idReporte, CrearComentarioDTO crearComentarioDTO) throws Exception {
        // Validar que el ID tenga formato correcto
        if (!ObjectId.isValid(idReporte)) {
            throw new IllegalArgumentException("El ID del reporte no es válido: " + idReporte);
        }
        ObjectId objectId = new ObjectId(idReporte);
        // Buscar el reporte
        Reporte reporte = reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));
        // Mapear CrearComentarioDTO a Comentario utilizando el mapper
        Comentario comentario = comentarioMapper.toDocument(crearComentarioDTO);
        // Generar un nuevo ID y establecer fecha actual
        comentario.setId(new ObjectId());
        comentario.setFecha(LocalDateTime.now());
        comentario.setReporteId(objectId);
        // Inicializar la lista de comentarios si está vacía
        if (reporte.getComentarios() == null) {
            reporte.setComentarios(new ArrayList<>());
        }
        // Agregar el comentario a la lista
        reporte.getComentarios().add(comentario);
        // Guardar el reporte actualizado
        reporteRepositorio.save(reporte);
    }

    @Override
    public List<ComentarioDTO> obtenerComentarios(String idReporte) throws Exception {
        if (!ObjectId.isValid(idReporte)) {
            throw new IllegalArgumentException("El ID del reporte no es válido: " + idReporte);
        }
        ObjectId objectId = new ObjectId(idReporte);
        Reporte reporte = reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));

        if (reporte.getComentarios() == null || reporte.getComentarios().isEmpty()) {
            return List.of();
        }
        return reporte.getComentarios().stream()
                .map(comentarioMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public void editarComentario(String idReporte, String idComentario, String nuevoMensaje) throws Exception {
        // Validar los IDs
        if (!ObjectId.isValid(idReporte) || !ObjectId.isValid(idComentario)) {
            throw new IllegalArgumentException("El ID del reporte o del comentario no es válido");
        }
        ObjectId reporteObjectId = new ObjectId(idReporte);
        ObjectId comentarioObjectId = new ObjectId(idComentario);
        // Buscar el reporte
        Reporte reporte = reporteRepositorio.findById(reporteObjectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));
        // Buscar el comentario dentro del reporte
        Comentario comentario = reporte.getComentarios().stream()
                .filter(c -> c.getId().equals(comentarioObjectId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No se encontró un comentario con el id: " + idComentario));
        // Actualizar el mensaje del comentario
        comentario.setMensaje(nuevoMensaje);
        // Guardar el reporte actualizado
        reporteRepositorio.save(reporte);
    }

}
