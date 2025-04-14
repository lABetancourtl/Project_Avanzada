package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.seguridad.JWTUtils;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticacionServicioImplTest {

    @InjectMocks
    private AutenticacionServicioImpl autenticacionServicio;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtils jwtUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginExitoso() throws Exception {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("test@example.com", "password123");

        Usuario usuario = new Usuario();
        usuario.setId(ObjectId.get());
        usuario.setEmail("test@example.com");
        usuario.setPassword("encodedPassword");
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setRol(Rol.CLIENTE);

        when(usuarioRepositorio.findByEmail(loginDTO.email())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(loginDTO.password(), usuario.getPassword())).thenReturn(true);
        when(jwtUtils.generateToken(anyString(), anyMap())).thenReturn("mocked-jwt-token");

        // Act
        TokenDTO result = autenticacionServicio.login(loginDTO);

        // Assert
        assertNotNull(result);
        assertEquals("mocked-jwt-token", result.token());
    }

    @Test
    void loginUsuarioNoEncontrado() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("notfound@example.com", "password123");
        when(usuarioRepositorio.findByEmail(loginDTO.email())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> autenticacionServicio.login(loginDTO));
        assertEquals("El correo no está registrado", exception.getMessage());
    }

    @Test
    void loginUsuarioNoActivo() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("test@example.com", "password123");

        Usuario usuario = new Usuario();
        usuario.setEstado(EstadoUsuario.INACTIVO); // O el estado que corresponda a no activo

        when(usuarioRepositorio.findByEmail(loginDTO.email())).thenReturn(Optional.of(usuario));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> autenticacionServicio.login(loginDTO));
        assertEquals("El usuario no esta activo", exception.getMessage());
    }

    @Test
    void loginContrasenaIncorrecta() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("test@example.com", "password123");

        Usuario usuario = new Usuario();
        usuario.setPassword("encodedPassword");
        usuario.setEstado(EstadoUsuario.ACTIVO);

        when(usuarioRepositorio.findByEmail(loginDTO.email())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(loginDTO.password(), usuario.getPassword())).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> autenticacionServicio.login(loginDTO));
        assertEquals("La contraseña es incorrecta", exception.getMessage());
    }
}

