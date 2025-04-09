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

    //aquui
    @Override
    public void crearComentario(String idReporte, CrearComentarioDTO crearComentarioDTO) throws Exception {

    }
    //Aqui
    @Override
    public List<ComentarioDTO> obtenerComentarios(String idReporte) throws Exception {
        return null;
    }

}
