package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.ValidarCodigoDTO;
import co.edu.uniquindio.proyecto.servicios.CodigoValidacionServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/validacion")
public class CodigoValidacionControlador {

    private final CodigoValidacionServicio codigoValidacionServicio;

    // Validar código de activación
    @PostMapping("/validar")
    public ResponseEntity<String> validarCodigo(@Valid @RequestBody ValidarCodigoDTO validacion) throws Exception {
        codigoValidacionServicio.validarCodigo(validacion);
        return ResponseEntity.ok("Cuenta activada correctamente");
    }

    // Reenviar código de activación
    @PostMapping("/reenviar/{email}")
    public ResponseEntity<String> reenviarCodigo(@PathVariable String email) throws Exception {
        codigoValidacionServicio.reenviarCodigo(email);
        return ResponseEntity.ok("Se ha enviado un nuevo código de activación a tu correo");
    }
}