package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.dto.ValidarCodigoDTO;
import co.edu.uniquindio.proyecto.servicios.AutenticacionServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements AutenticacionServicio {
    @Override
    public TokenDTO login(LoginDTO login) throws Exception {
        return null;
    }
//    @Override
//    public void validarCodigo(ValidarCodigoDTO validacion) {
//
//    }
//
//    @Override
//    public void reenviarCodigo(String email) {
//
//    }
}
