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


    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<UsuarioDTO>> obtener(@PathVariable String id) throws Exception {
        UsuarioDTO dto = usuarioServicio.obtener(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, dto));
    }

    // Listar usuarios con filtros y paginación
    @GetMapping
    public ResponseEntity<MensajeDTO<List<UsuarioDTO>>> listarTodos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String ciudad,
            @RequestParam(defaultValue = "0") int pagina
    ) {
        List<UsuarioDTO> lista = usuarioServicio.listarTodos(nombre, ciudad, pagina);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    // Registro de usuario
    @PostMapping
    public ResponseEntity<MensajeDTO<String>> crear(@Valid @RequestBody CrearUsuarioDTO cuenta) throws Exception {
        usuarioServicio.crear(cuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Registro exitoso. Verifica tu correo para activar tu cuenta."));
    }

    @PostMapping("/codigoVerificacion")
    public ResponseEntity<MensajeDTO<String>> enviarCodigoVerificacion(@RequestBody EnviarCodigoDTO enviarCodigoDTO) throws Exception {
        usuarioServicio.enviarCodigoVerificacion(enviarCodigoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Codigo enviado correctamente."));
    }

    // Edición de perfil
    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> editar(@PathVariable String id, @Valid @RequestBody EditarUsuarioDTO editarUsuarioDTO) throws Exception {
        usuarioServicio.editar(id,editarUsuarioDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Perfil actualizado correctamente."));
    }

    @PutMapping("/cambiarpassword")
    public ResponseEntity<MensajeDTO<String>> cambiarPassword(@RequestBody CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
        usuarioServicio.cambiarPassword(cambiarPasswordDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Password cambiado correctamente."));
    }

    @PutMapping("/activar")
    public ResponseEntity<MensajeDTO<String>> activarCuenta(@RequestBody ActivarCuentaDTO activarCuentaDTO) throws Exception {
        usuarioServicio.activarCuenta(activarCuentaDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Activado correctamente."));
    }

    // Eliminación lógica de cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminar(@PathVariable String id) throws Exception {
        usuarioServicio.eliminar(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta eliminada correctamente."));
    }

    //-----------------Preguntar al profesor----------------------------------------


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
