package co.edu.uniquindio.proyecto;

import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RepositoriosTest {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Test
    public void crearUsuarioTest() {
        Usuario usuario = Usuario.builder()
                .email("correo" + System.currentTimeMillis() + "@correo.com")
                .rol(Rol.CLIENTE)
                .telefono("3215")
                .password("123456")
                .nombre("Anderson")
                .ciudad("Armenia")
                .build();

        Usuario usuarioGuardado = usuarioRepositorio.save(usuario);

        assertNotNull(usuarioGuardado.getId(), "El ID no fue generado");
    }
}

