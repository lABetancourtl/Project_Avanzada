package co.edu.uniquindio.proyecto.servicios.impl;


import co.edu.uniquindio.proyecto.dto.CrearUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.EditarUsuarioDTO;
import co.edu.uniquindio.proyecto.dto.UsuarioDTO;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class UsuarioServicioImpl implements UsuarioServicio {

   private final List<UsuarioDTO> usuarios = new ArrayList<>();

    public UsuarioServicioImpl() {
        usuarios.add(new UsuarioDTO(1001L, "Juan Pérez", "3112345678", "Bogotá", "Calle 123", "juan@example.com"));
        usuarios.add(new UsuarioDTO(1002L, "María López", "3209876543", "Medellín", "Carrera 45", "maria@example.com"));
        usuarios.add(new UsuarioDTO(1003L, "Carlos Gómez", "3001234567", "Cali", "Avenida 10", "carlos@example.com"));
        usuarios.add(new UsuarioDTO(1004L, "Ana Torres", "3107654321", "Barranquilla", "Calle 50", "ana@example.com"));
        usuarios.add(new UsuarioDTO(1005L, "Pedro Ramírez", "3228765432", "Cartagena", "Carrera 12", "pedro@example.com"));
        usuarios.add(new UsuarioDTO(1006L, "Laura Sánchez", "3005432176", "Bucaramanga", "Avenida 5", "laura@example.com"));
        usuarios.add(new UsuarioDTO(1007L, "Luis Fernández", "3116783456", "Pereira", "Calle 25", "luis@example.com"));
        usuarios.add(new UsuarioDTO(1008L, "Sofía Martínez", "3187654321", "Manizales", "Carrera 8", "sofia@example.com"));
        usuarios.add(new UsuarioDTO(1009L, "Andrés Ríos", "3131237890", "Armenia", "Avenida Bolívar", "andres@example.com"));
        usuarios.add(new UsuarioDTO(1010L, "Gabriela Herrera", "3145678901", "Neiva", "Calle 30", "gabriela@example.com"));
    }




    
    @Override
    public void crear(CrearUsuarioDTO cuenta) {

    }

    @Override
    public void editar(EditarUsuarioDTO cuenta) {

    }

    @Override
    public void eliminar(String id) {

    }

    @Override
    public UsuarioDTO obtener(String id) {
        return null;
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        return usuarios;
    }
}
