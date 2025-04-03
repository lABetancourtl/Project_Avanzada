package co.edu.uniquindio.proyecto.mapper;

import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
import co.edu.uniquindio.proyecto.dto.ReporteDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReporteMapper {

    Reporte toDocument(CrearReporteDTO dto);
    //  ReporteDTO toDTO(Reporte reporte);

    default ObjectId map(String value) {
        return new ObjectId(value);
    }

}
