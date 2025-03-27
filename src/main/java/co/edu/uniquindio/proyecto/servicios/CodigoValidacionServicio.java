package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.ValidarCodigoDTO;
import org.springframework.stereotype.Service;

@Service
public interface CodigoValidacionServicio {
    
    void validarCodigo(ValidarCodigoDTO validacion) throws Exception;
    
    void reenviarCodigo(String email) throws Exception;
}