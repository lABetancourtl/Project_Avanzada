package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import co.edu.uniquindio.proyecto.dto.InfocategoriaDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoriaServicio {
    void crearCategoria(CrearCategoriaDTO categoria) throws Exception;
    void actualizarCategoria(String id, CategoriaDTO categoria) throws Exception;
    void editarCategoria(String id, @Valid CrearCategoriaDTO categoria) throws Exception;
    void eliminarCategoria(String id) throws Exception;
    InfocategoriaDTO obtenerCategoria(String id) throws Exception;
    List<CategoriaDTO> listar();
}
