package co.edu.uniquindio.proyecto.seguridad;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JWTUtilsHelper {

    /**
     * Devuelve el ID del usuario autenticado extraído del token JWT.
     */
    public String obtenerIdUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Este 'name' es tu ID del usuario, porque así lo generaste en tu JWT
    }
}
