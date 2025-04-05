package co.edu.uniquindio.proyecto.mapper;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toDocument(CrearCategoriaDTO dto);
    // Para listar: de documento a DTO
    CategoriaDTO toDTO(Categoria categoria);
}
