package co.edu.uniquindio.proyecto.mapper;

import co.edu.uniquindio.proyecto.dto.CrearUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.EditarUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.UsuarioDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import org.bson.types.ObjectId;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "rol", constant = "CLIENTE")
    @Mapping(target = "estado", constant = "INACTIVO")
    @Mapping(target = "fechaRegistro", expression = "java(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME))")
    Usuario toDocument(CrearUsuarioDTO dto); // Convierte DTO de creación a documento

    UsuarioDTO toDTO(Usuario usuario);

    // Conversión personalizada: ObjectId → String
    default String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "codigoValidacion", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    void editarUsuarioDTO(EditarUsuarioDTO dto, @MappingTarget Usuario usuario);
}