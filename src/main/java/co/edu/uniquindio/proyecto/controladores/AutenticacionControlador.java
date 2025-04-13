package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.dto.ValidarCodigoDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.servicios.AutenticacionServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AutenticacionControlador {

    private final AutenticacionServicio autenticacionServicio;

    @PostMapping("/login")
    public ResponseEntity<MensajeDTO<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        TokenDTO token = autenticacionServicio.login(loginDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, token));
    }
    // 🚀 Opcional: Activación de cuenta (ya lo tenías)
    // @PostMapping("/validar")
    // public ResponseEntity<String> validarCodigo(@Valid @RequestBody ValidarCodigoDTO validacion) throws Exception {
    //     autenticacionServicio.validarCodigo(validacion);
    //     return ResponseEntity.ok("Cuenta activada correctamente");
    // }

    // 🚀 Opcional: Reenviar código de activación
    // @PostMapping("/reenviar/{email}")
    // public ResponseEntity<String> reenviarCodigo(@PathVariable String email) throws Exception {
    //     autenticacionServicio.reenviarCodigo(email);
    //     return ResponseEntity.ok("Se ha enviado un nuevo código de activación a tu correo");
    // }
}
