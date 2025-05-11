package co.edu.uniquindio.proyecto.mapper;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Categoria;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    default String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }



    Categoria toDocument(CrearCategoriaDTO dto);
    // Para listar: de documento a DTO
    @Mapping(target = "id", source = "id") // Usará el método map(ObjectId)
    CategoriaDTO toDTO(Categoria categoria);

}
