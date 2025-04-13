package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import co.edu.uniquindio.proyecto.dto.InfocategoriaDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.servicios.CategoriaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categorias")
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;

    // Crear una nueva categoría (solo administradores)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<MensajeDTO<String>>  crear(@Valid @RequestBody CrearCategoriaDTO categoria) throws Exception {
        categoriaServicio.crearCategoria(categoria);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Categoría creada exitosamente"));
    }

    // Editar una categoría existente
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>>  editar(@PathVariable String id, @Valid @RequestBody CrearCategoriaDTO categoria) throws Exception {
        categoriaServicio.editarCategoria(id, categoria);
        return ResponseEntity.ok(new MensajeDTO<>(false ,"Categoría actualizada correctamente"));
    }

    // Eliminar una categoría
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>>  eliminar(@PathVariable String id) throws Exception {
        categoriaServicio.eliminarCategoria(id);
        return ResponseEntity.ok(new MensajeDTO<>(false ,"Categoría eliminada correctamente"));
    }

    // Listar todas las categorías
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        List<CategoriaDTO> categorias = categoriaServicio.listar();
        return ResponseEntity.ok(categorias);
    }
}