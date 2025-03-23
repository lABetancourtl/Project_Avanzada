package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;

import java.util.List;

public interface CategoriaServicio {
    
    String crear(CrearCategoriaDTO categoria) throws Exception;
    
    void editar(String id, CrearCategoriaDTO categoria) throws Exception;
    
    void eliminar(String id) throws Exception;
    
    List<CategoriaDTO> listar();
}