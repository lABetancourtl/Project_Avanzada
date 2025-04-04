package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;

import java.util.List;

public interface CategoriaServicio {
    void crear(@Valid CrearCategoriaDTO categoria) throws Exception;

    void editar(String id, @Valid CrearCategoriaDTO categoria);

    void eliminar(String id) throws Exception;

    List<CategoriaDTO> listar();
}
