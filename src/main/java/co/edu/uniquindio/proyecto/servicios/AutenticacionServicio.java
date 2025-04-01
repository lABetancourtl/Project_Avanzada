package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.ValidarCodigoDTO;
import jakarta.validation.Valid;

public interface AutenticacionServicio {
    void validarCodigo(@Valid ValidarCodigoDTO validacion);

    void reenviarCodigo(String email);
}
