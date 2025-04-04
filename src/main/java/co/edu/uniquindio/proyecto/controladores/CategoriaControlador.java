package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.servicios.CategoriaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
    public ResponseEntity<MensajeDTO<String>>  crear(@Valid @RequestBody CrearCategoriaDTO categoria) throws Exception {
        categoriaServicio.crear(categoria);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Categoría creada exitosamente"));
    }

    // Editar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>>  editar(@PathVariable String id, @Valid @RequestBody CrearCategoriaDTO categoria) throws Exception {
        categoriaServicio.editar(id, categoria);
        return ResponseEntity.ok(new MensajeDTO<>(false ,"Categoría actualizada correctamente"));
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>>  eliminar(@PathVariable String id) throws Exception {
        categoriaServicio.eliminar(id);
        return ResponseEntity.ok(new MensajeDTO<>(false ,"Categoría eliminada correctamente"));
    }

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        List<CategoriaDTO> categorias = categoriaServicio.listar();
        return ResponseEntity.ok(categorias);
    }
}