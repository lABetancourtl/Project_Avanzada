package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import co.edu.uniquindio.proyecto.servicios.CategoriaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicioImpl implements CategoriaServicio {
    @Override
    public String crear(CrearCategoriaDTO categoria) {
        return "";
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
