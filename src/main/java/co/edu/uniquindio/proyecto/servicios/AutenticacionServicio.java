package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.dto.ValidarCodigoDTO;
import jakarta.validation.Valid;

public interface AutenticacionServicio {

    TokenDTO login(@Valid LoginDTO login) throws Exception;
}
