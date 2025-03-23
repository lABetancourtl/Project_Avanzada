package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.ValidarCodigoDTO;

public interface CodigoValidacionServicio {
    
    void validarCodigo(ValidarCodigoDTO validacion) throws Exception;
    
    void reenviarCodigo(String email) throws Exception;
}