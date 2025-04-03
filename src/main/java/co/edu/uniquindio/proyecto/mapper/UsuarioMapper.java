package co.edu.uniquindio.proyecto.mapper;

import co.edu.uniquindio.proyecto.dto.CrearUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.EditarUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.UsuarioDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import org.bson.types.ObjectId;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Convierte DTO de creación a documento
    Usuario toDocument(CrearUsuarioDTO dto);

    UsuarioDTO toDTO(Usuario usuario);

    // Conversión personalizada: ObjectId → String
    default String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }

    // Actualiza un documento existente con los valores del DTO (para editar)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void editarUsuarioDTO(EditarUsuarioDTO dto, @MappingTarget Usuario usuario);

}

