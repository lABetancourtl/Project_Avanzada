package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import co.edu.uniquindio.proyecto.dto.InfocategoriaDTO;
import co.edu.uniquindio.proyecto.mapper.CategoriaMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Categoria;
import co.edu.uniquindio.proyecto.repositorios.CategoriaRepositorio;
import co.edu.uniquindio.proyecto.servicios.CategoriaServicio;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicioImpl implements CategoriaServicio {

    private  final CategoriaRepositorio categoriaRepositorio;
    private final CategoriaMapper categoriaMapper;


    @Override
    public void crearCategoria(CrearCategoriaDTO dto) throws Exception {
        // Validar si ya existe una categoría con el mismo nombre (opcional pero recomendable)
        if (categoriaRepositorio.existsByNombreIgnoreCase(dto.nombre())) {
            throw new Exception("Ya existe una categoría con el nombre: " + dto.nombre());
        }
        Categoria categoria = categoriaMapper.toDocument(dto);
        categoriaRepositorio.save(categoria);
    }

    @Override
    public void actualizarCategoria(String id, CategoriaDTO categoria) throws Exception {

    }

    @Override
    public void editarCategoria(String id, CrearCategoriaDTO categoriaDTO) throws Exception {
        Categoria categoriaExistente = categoriaRepositorio.findById(new ObjectId(id))
                .orElseThrow(() -> new Exception("La categoría con ID " + id + " no existe"));
        boolean existeOtra = categoriaRepositorio.existsByNombreIgnoreCase(categoriaDTO.nombre())
                && !categoriaExistente.getNombre().equalsIgnoreCase(categoriaDTO.nombre());
        if (existeOtra) {
            throw new Exception("Ya existe una categoría con el nombre: " + categoriaDTO.nombre());
        }
        categoriaExistente.setNombre(categoriaDTO.nombre());
        categoriaExistente.setIcono(categoriaDTO.icono());
        categoriaRepositorio.save(categoriaExistente);
    }

    @Override
    public void eliminarCategoria(String id) throws Exception {
        ObjectId objectId = new ObjectId(id);
        if (!categoriaRepositorio.existsById(objectId)) {
            throw new Exception("No se encontró una categoría con el ID: " + objectId);
        }
        categoriaRepositorio.deleteById(objectId);
    }

    @Override
    public InfocategoriaDTO obtenerCategoria(String id) throws Exception {
        return null;
    }

    @Override
    public List<CategoriaDTO> listar() {
        return categoriaRepositorio.findAll()
                .stream()
                .map(categoriaMapper::toDTO)
                .toList();
    }

}
