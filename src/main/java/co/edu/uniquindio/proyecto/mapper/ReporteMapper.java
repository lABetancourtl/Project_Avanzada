package co.edu.uniquindio.proyecto.mapper;

import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
import co.edu.uniquindio.proyecto.dto.EditarReporteDTO;
import co.edu.uniquindio.proyecto.dto.ReporteDTO;
import co.edu.uniquindio.proyecto.dto.UbicacionDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.vo.Ubicacion;
import org.bson.types.ObjectId;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReporteMapper {

    @Mapping(target = "ubicacion", source = "ubicacion")
    Reporte toDocument(CrearReporteDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clienteId", ignore = true)
    @Mapping(target = "categoriaId", ignore = true)
    @Mapping(target = "estadoActual", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "contadorImportante", ignore = true)
    @Mapping(target = "historial", ignore = true)
    void EditarReporteDTO(EditarReporteDTO dto, @MappingTarget Reporte reporte);
    // Conversión personalizada de String a ObjectId
    default ObjectId map(String value) {
        return new ObjectId(value);
    }
    // ✅ Conversión personalizada de ObjectId a String (para toDTO)
    default String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }

    // Conversión personalizada para ubicación
    default Ubicacion mapUbicacionDTO(UbicacionDTO dto) {
        return new Ubicacion(dto.latitud(), dto.longitud());
    }
    // Conversión de documento a DTO
    ReporteDTO toDTO(Reporte reporte);
}

