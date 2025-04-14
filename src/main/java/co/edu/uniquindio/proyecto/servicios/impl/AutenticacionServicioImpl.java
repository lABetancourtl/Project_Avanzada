package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.dto.ValidarCodigoDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.seguridad.JWTUtils;
import co.edu.uniquindio.proyecto.servicios.AutenticacionServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements AutenticacionServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenDTO login(LoginDTO loginDTO) throws Exception {
        // 1. Buscar el usuario por email
        Usuario usuario = usuarioRepositorio.findByEmail(loginDTO.email())
                .orElseThrow(() -> new Exception("El correo no está registrado"));
        if (!usuario.getEstado().equals(EstadoUsuario.ACTIVO)) {
            throw new Exception("El usuario no esta activo");
        }

        // 2. Verificar contraseña, ya viene cifrada desde el back
        if (!passwordEncoder.matches(loginDTO.password(), usuario.getPassword())) {
            throw new Exception("La contraseña es incorrecta");
        }
        // 3. Crear el mapa de claims con los datos que quieres incluir en el token
        Map<String, String> claims = Map.of(
                "id", usuario.getId().toString(),
                "email", usuario.getEmail(),
                "rol", usuario.getRol().name()
        );
        // 4. Generar token con tu método de JWTUtils
        String jwtToken = jwtUtils.generateToken(usuario.getId().toString(), claims);

        // 5. Retornar el token en el DTO
        return new TokenDTO(jwtToken, null);
    }
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

