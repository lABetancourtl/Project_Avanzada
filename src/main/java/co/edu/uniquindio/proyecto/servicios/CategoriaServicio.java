package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoriaServicio {
    String crear(@Valid CrearCategoriaDTO categoria);

    void editar(String id, @Valid CrearCategoriaDTO categoria);

    void eliminar(String id);

    List<CategoriaDTO> listar();
}
