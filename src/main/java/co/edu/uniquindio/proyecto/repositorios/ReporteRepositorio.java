package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.dto.ReporteDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepositorio extends MongoRepository<Reporte, ObjectId> {


    // Buscar reportes por estado
    List<Reporte> findByEstadoActual(String estadoActual);

    // Buscar reportes que contengan una palabra clave en el título
    List<Reporte> findByTituloContainingIgnoreCase(String titulo);

    // Buscar reportes en una categoría específica
    List<Reporte> findByCategoriaId(ObjectId categoriaId);
    List<Reporte> findByCategoriaId(String categoriaId);
    List<Reporte> findAllByClienteId(ObjectId clienteId);
    List<Reporte> findByCiudadIgnoreCase(String ciudad);

    // filter, lookup categorias, lookup usuarios, project
    @Aggregation(
            {
                "{ $match: { ciudad: 'Armenia' }  }",
                "{ $lookup: { from: 'categorias', localField: 'categoriaId', foreignField: '_id', as: 'categoria' } }",
                "{ $unwind: '$categoria' }",
                "{ $lookup: { from: 'usuarios', localField: 'clienteId', foreignField: '_id', as: 'usuario' } }",
                "{ $unwind: '$usuario' }",
                "{ $project: { id: '$_id', titulo: '$titulo', descripcion: '$descripcion', estadoActual: '$estadoActual', fecha: '$fecha', clienteId: '$clienteId', nombreCliente: '$usuario.nombre', ubicacion: '$ubicacion', categoriaId: '$categoriaId', nombreCategoria: '$categoria.nombre' , fotos: '$fotos'} }"
            }
    )
    List<ReporteDTO> obtenerReportes(String nombreCiudad);

}