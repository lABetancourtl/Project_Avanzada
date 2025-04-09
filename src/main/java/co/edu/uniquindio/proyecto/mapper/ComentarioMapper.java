package co.edu.uniquindio.proyecto.mapper;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Comentario;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    // Entrada: CrearComentarioDTO -> Comentario
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "reporteId", ignore = true)
    @Mapping(target = "usuarioId", source = "clienteId")
    Comentario toDocument(CrearComentarioDTO crearComentarioDTO);

    // Salida: Comentario -> ComentarioDTO
    @Mapping(source = "usuarioId", target = "clienteId")
    ComentarioDTO toDTO(Comentario comentario);

    // Conversión personalizada
    default ObjectId map(String value) {
        return value != null ? new ObjectId(value) : null;
    }

    default String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }
}
