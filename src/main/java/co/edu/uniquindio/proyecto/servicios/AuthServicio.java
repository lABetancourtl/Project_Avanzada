package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthServicio {
    
    TokenDTO login(LoginDTO loginDTO) throws Exception;
    
    void logout(String token) throws Exception;
}