package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearUsuarioDTO;
import co.edu.uniquindio.proyecto.mapper.CategoriaMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Categoria;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;
import co.edu.uniquindio.proyecto.repositorios.CategoriaRepositorio;
import co.edu.uniquindio.proyecto.servicios.CategoriaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicioImpl implements CategoriaServicio {

    private  final CategoriaRepositorio categoriaRepositorio;
    private final CategoriaMapper categoriaMapper;


    @Override
    public void crear(CrearCategoriaDTO dto) throws Exception {
        // Validar si ya existe una categoría con el mismo nombre (opcional pero recomendable)
        if (categoriaRepositorio.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new Exception("Ya existe una categoría con el nombre: " + dto.getNombre());
        }
        Categoria categoria = categoriaMapper.toDocument(dto);
        categoriaRepositorio.save(categoria);
    }


    @Override
    public void editar(String id, CrearCategoriaDTO categoria) {

    }

    @Override
    public void eliminar(String id) {

    }

    @Override
    public List<CategoriaDTO> listar() {
        return List.of();
    }
}
