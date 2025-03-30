package co.edu.uniquindio.proyecto.mapper;

import co.edu.uniquindio.proyecto.dto.CrearUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.UsuarioDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "estado", constant = "INACTIVO")
    @Mapping(target = "rol", constant = "CLIENTE")
    Usuario toDocument(CrearUsuarioDTO dto);

    UsuarioDTO toDTO(Usuario usuario);
}
