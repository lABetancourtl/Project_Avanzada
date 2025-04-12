package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.servicios.ComentarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioControlador {

    private final ComentarioServicio comentarioServicio;

    @PostMapping("/{idReporte}")
    public void crearComentario(@PathVariable String idReporte, @RequestBody CrearComentarioDTO comentarioDTO) throws Exception {
        comentarioServicio.crearComentario(idReporte, comentarioDTO);
    }

    @PutMapping("/{idReporte}/{idComentario}")
    public ResponseEntity<String> editarComentario(
            @PathVariable String idReporte,
            @PathVariable String idComentario,
            @RequestParam String nuevoMensaje
    ) throws Exception {
        comentarioServicio.editarComentario(idReporte, idComentario, nuevoMensaje);
        return ResponseEntity.ok("Comentario actualizado correctamente");
    }

    @GetMapping("/{idReporte}")
    public List<ComentarioDTO> obtenerComentarios(@PathVariable String idReporte) throws Exception {
        return comentarioServicio.obtenerComentarios(idReporte);
    }
}
