package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.CrearUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.EditarUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.UsuarioDTO;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    // Registro de usuario
    @PostMapping
    public ResponseEntity<MensajeDTO<String>> crear(@Valid @RequestBody CrearUsuarioDTO cuenta) throws Exception {
        usuarioServicio.crear(cuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Registro exitoso. Verifica tu correo para activar tu cuenta."));
    }


    // Edición de perfil
    @PutMapping
    public ResponseEntity<String> editar(@Valid @RequestBody EditarUsuarioDTO cuenta) throws Exception {
        usuarioServicio.editar(cuenta);
        return ResponseEntity.ok("Perfil actualizado correctamente.");
    }

    // Eliminación de cuenta (cambio de estado)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) throws Exception {
        usuarioServicio.eliminar(id);
        return ResponseEntity.ok("Cuenta eliminada correctamente.");
    }

    // Obtener información de un usuario específico
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable String id) throws Exception {
        UsuarioDTO info = usuarioServicio.obtener(id);
        return ResponseEntity.ok(info);
    }

    // Listar usuarios con filtros opcionales
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String ciudad,
            @RequestParam(defaultValue = "0") int pagina
    ) {
//        List<UsuarioDTO> lista = usuarioServicio.listarTodos(nombre, ciudad, pagina);
//        return ResponseEntity.ok(lista);
        return null;
    }
}
