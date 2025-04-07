package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.servicios.ComentarioServicio;

import java.util.List;

public class ComentarioServicioImpl implements ComentarioServicio {
    @Override
    public List<ComentarioDTO> obtenerComentarios(String idReporte) throws Exception {
        return List.of();
    }

    @Override
    public void crearComentario(String idReporte, ComentarioDTO comentarioDTO) throws Exception {

    }
}
