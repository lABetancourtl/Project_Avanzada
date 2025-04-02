package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> editar(@Valid @RequestBody EditarUsuarioDTO cuenta) throws Exception {
        usuarioServicio.editar(cuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Perfil actualizado correctamente."));
    }

    // Eliminación lógica de cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminar(@PathVariable String id) throws Exception {
        usuarioServicio.eliminar(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta eliminada correctamente."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<UsuarioDTO>> obtener(@PathVariable String id) throws Exception {
        UsuarioDTO dto = usuarioServicio.obtener(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, dto));
    }


    @PostMapping("/{email}/codigoVerificacion")
    public ResponseEntity<MensajeDTO<String>> enviarCodigoVerificacion(@PathVariable String email) throws Exception {
        usuarioServicio.enviarCodigoVerificacion(email);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Codigo enviado correctamente."));
    }

    @PutMapping("/{email}/password")
    public ResponseEntity<MensajeDTO<String>> cambiarPassword(@PathVariable String email, @RequestBody CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
        usuarioServicio.cambiarPassword(email, cambiarPasswordDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Password cambiado correctamente."));
    }

    @PutMapping("/{email}/activar")
    public ResponseEntity<MensajeDTO<String>> activarCuenta(@PathVariable String email, @RequestBody ActivarCuentaDTO activarCuentaDTO) throws Exception {
        usuarioServicio.activarCuenta(email, activarCuentaDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Activado correctamente."));
    }

    @GetMapping("/{email}/reportes")
    public ResponseEntity<MensajeDTO<List<InfoReporteDTO>>> obtenerReportesUsuario(@PathVariable String id) throws Exception {
        List<InfoReporteDTO> lista = usuarioServicio.obtenerReportesUsuario(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    //-----------------Preguntar al profesor----------------------------------------

    // Listar usuarios con filtros y paginación
//    @GetMapping
//    public ResponseEntity<MensajeDTO<List<UsuarioDTO>>> listarTodos(
//            @RequestParam(required = false) String nombre,
//            @RequestParam(required = false) String ciudad,
//            @RequestParam(defaultValue = "0") int pagina
//    ) {
//        List<UsuarioDTO> lista = usuarioServicio.listarTodos(nombre, ciudad, pagina);
//        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
//    }
//
//    // Login de usuario
//    @PostMapping("/login")
//    public ResponseEntity<MensajeDTO<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
//        TokenDTO token = usuarioServicio.login(loginDTO);
//        return ResponseEntity.ok(new MensajeDTO<>(false, token));
//    }

    // Validar si un email ya está registrado
//    @GetMapping("/validar-email")
//    public ResponseEntity<MensajeDTO<Boolean>> validarEmail(@RequestParam String email) {
//        boolean existe = usuarioServicio.validarEmail(email);
//        return ResponseEntity.ok(new MensajeDTO<>(false, existe));
//    }
}
