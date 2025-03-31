package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
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
    @PostMapping
    public ResponseEntity<String> crear(@Valid @RequestBody CrearCategoriaDTO categoria) throws Exception {
        String id = categoriaServicio.crear(categoria);
        return ResponseEntity.ok("Categoría creada exitosamente");
    }

    // Editar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable String id, @Valid @RequestBody CrearCategoriaDTO categoria) throws Exception {
        categoriaServicio.editar(id, categoria);
        return ResponseEntity.ok("Categoría actualizada correctamente");
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) throws Exception {
        categoriaServicio.eliminar(id);
        return ResponseEntity.ok("Categoría eliminada correctamente");
    }

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        List<CategoriaDTO> categorias = categoriaServicio.listar();
        return ResponseEntity.ok(categorias);
    }
}