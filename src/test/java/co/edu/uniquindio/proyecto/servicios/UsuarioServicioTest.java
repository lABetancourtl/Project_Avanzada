package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.CrearUsuarioDTO;
import co.edu.uniquindio.proyecto.mapper.UsuarioMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.servicios.impl.UsuarioServicioImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServicioTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioServicioImpl usuarioServicio;

    @Test
    public void testCrearUsuario_exitoso() throws Exception {
        // Arrange
        CrearUsuarioDTO dto = new CrearUsuarioDTO();
        dto.setNombre("Kevin");
        dto.setEmail("kevin@mail.com");
        dto.setPassword("1234");
        dto.setTelefono("3001234567");
        dto.setCiudad("Armenia");
        dto.setDireccion("Cra 10 #20-30");

        when(usuarioRepositorio.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        Usuario usuarioMapeado = Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .telefono(dto.getTelefono())
                .ciudad(dto.getCiudad())
                .direccion(dto.getDireccion())
                .build();

        when(usuarioMapper.toDocument(dto)).thenReturn(usuarioMapeado);

        // Act
        usuarioServicio.crear(dto);

        // Assert
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepositorio).save(captor.capture());

        Usuario guardado = captor.getValue();
        assertEquals(dto.getEmail(), guardado.getEmail());
        assertEquals(Rol.CLIENTE, guardado.getRol());
        assertEquals(EstadoUsuario.INACTIVO, guardado.getEstado());
    }

    @Test
    public void testCrearUsuario_correoYaExiste() {
        // Arrange
        CrearUsuarioDTO dto = new CrearUsuarioDTO();
        dto.setEmail("yaexiste@mail.com");

        when(usuarioRepositorio.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(new Usuario()));

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> usuarioServicio.crear(dto));
        assertEquals("El correo ya está registrado", ex.getMessage());

        verify(usuarioRepositorio, never()).save(any());
    }
}
