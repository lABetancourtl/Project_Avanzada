package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.servicios.ComentarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comentarios")
public class ComentarioControlador {

    private final ComentarioServicio comentarioServicio;

    // Crear un nuevo comentario
    @PostMapping
    public ResponseEntity<String> crear(@Valid @RequestBody CrearComentarioDTO comentario) throws Exception {
        String id = comentarioServicio.crear(comentario);
        return ResponseEntity.ok("Comentario creado exitosamente");
    }

    // Eliminar un comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) throws Exception {
        comentarioServicio.eliminar(id);
        return ResponseEntity.ok("Comentario eliminado correctamente");
    }

    // Obtener comentarios por reporte
    @GetMapping("/reporte/{reporteId}")
    public ResponseEntity<List<ComentarioDTO>> listarPorReporte(@PathVariable String reporteId) {
        List<ComentarioDTO> comentarios = comentarioServicio.listarPorReporte(reporteId);
        return ResponseEntity.ok(comentarios);
    }
}