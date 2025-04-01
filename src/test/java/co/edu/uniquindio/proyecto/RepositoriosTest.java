package co.edu.uniquindio.proyecto;

import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RepositoriosTest {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Test
    public void crearUsuarioTest() {
        Usuario usuario = Usuario.builder()
                .email("correo@correo.com")
                .rol(Rol.CLIENTE)
                .telefono("3215")
                .password("123456")
                .nombre("Anderson")
                .ciudad("Armenia")
                .build();
         usuarioRepositorio.save(usuario);
    }

    @Test
    public void actualizarUsuario1Test() {
        Usuario usuario = Usuario.builder()
                .email("clara@correo.com")
                .rol(Rol.CLIENTE)
                .telefono("9999")
                .password("999999")
                .nombre("Clara")
                .ciudad("Valencia")
                .build();
        Usuario usuarioCreado = usuarioRepositorio.save(usuario);
        usuarioCreado.setTelefono("3113365449");
        usuarioRepositorio.save(usuario);
    }

    @Test
    public void actualizarUsuario2Test() {

        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario primerUsuario = usuarios.getFirst();
        primerUsuario.setTelefono("NUEVO");
        usuarioRepositorio.save(primerUsuario);
    }

    @Test
    public void eliminarUsuarioTest() {

        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario primerUsuario = usuarios.getFirst();
        usuarioRepositorio.delete(primerUsuario);

        usuarioRepositorio.deleteById(new ObjectId("67e9b7dd3588a971fdb65b18"));  //otra opcion de eliminar dado el id
    }

    @Test
    public void buscarUsuarioTest() {

        // Forma 1 usando if
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(new ObjectId("67e9b7dd3588a971fdb65b18"));
        if (usuarioOptional.isPresent()) {
            System.out.println(usuarioOptional.get());
        } else {
            System.out.println("No se encontro el usuario");
        }

        // Forma 2 que usa una validacion interna con el orElseThrow
        Usuario usuarioOptional2 = usuarioRepositorio.
                findById(new ObjectId("67e9b7dd3588a971fdb65b18"))
                .orElseThrow( () -> new RuntimeException("No se encontro el usuario"));
                System.out.println(usuarioOptional2);

    }

}

