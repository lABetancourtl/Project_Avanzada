package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.excepciones.RecursoNoEncontradoException;
import co.edu.uniquindio.proyecto.mapper.UsuarioMapper;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.modelo.enums.Rol;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;


class UsuarioServicioImplTest {

    @InjectMocks
    private UsuarioServicioImpl usuarioServicio;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private UsuarioMapper usuarioMapper;

    private Validator validator;

    @BeforeEach
    void setUp() {
        // Inicializa mocks de Mockito
        MockitoAnnotations.openMocks(this);

        // Inicializa validador de Bean Validation
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    //Test de crear
    @Test
    void crearUsuarioExitosamente() throws Exception {
        // Preparación de datos
        CrearUsuarioDTO crearUsuarioDTO = new CrearUsuarioDTO(
                "Juan Pérez",
                "juan@example.com",
                "password123",
                "3110000000",
                "Armenia"
        );
        Usuario usuario = new Usuario();
        usuario.setEmail(crearUsuarioDTO.email());
        // Configuración de mocks
        when(usuarioRepositorio.findByEmail(crearUsuarioDTO.email())).thenReturn(Optional.empty());
        when(usuarioMapper.toDocument(crearUsuarioDTO)).thenReturn(usuario);
        // Ejecución
        usuarioServicio.crear(crearUsuarioDTO);
        // Verificación
        verify(usuarioRepositorio).save(usuario);
    }
    @Test
    void crearUsuarioConEmailDuplicadoDebeLanzarExcepcion() {
        // Arrange (Preparación de datos y mocks)
        CrearUsuarioDTO crearUsuarioDTO = new CrearUsuarioDTO(
                "Ana López",
                "ana@example.com",
                "password123",
                "3200000000",
                "Bogotá"
        );
        Usuario usuarioExistente = new Usuario();
        when(usuarioRepositorio.findByEmail(crearUsuarioDTO.email()))
                .thenReturn(Optional.of(usuarioExistente));
        // Act (Ejecución)
        Exception exception = assertThrows(Exception.class, () -> usuarioServicio.crear(crearUsuarioDTO));
        // Assert (Verificación)
        assertEquals("El correo ana@example.com ya está en uso", exception.getMessage());
        verify(usuarioRepositorio, never()).save(any(Usuario.class));
    }
    @Test
    void crearUsuarioConDatosInvalidosDebeFallar() {
        // Arrange
        CrearUsuarioDTO crearUsuarioDTO = new CrearUsuarioDTO(
                "",                // Nombre vacío
                "correo-no-valido", // Email mal formado
                "123",              // Contraseña demasiado corta
                "",                 // Teléfono vacío
                ""                  // Ciudad vacía
        );
        // Act
        var violaciones = validator.validate(crearUsuarioDTO);
        // Assert
        assertFalse(violaciones.isEmpty(), "Se esperaban violaciones de validación");
        assertEquals(5, violaciones.size()); // Opcional: válida que haya 5 errores exactamente
    }
    //Test de editar
    @Test
    void editarUsuarioExitosamente() throws Exception {
        // Arrange
        String id = new ObjectId().toString();
        EditarUsuarioDTO editarUsuarioDTO = new EditarUsuarioDTO(
                "Kevin Actualizado",
                "3100000000",
                "Armenia"
        );
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(new ObjectId(id));
        usuarioExistente.setNombre("Kevin Original");
        usuarioExistente.setTelefono("3200000000");
        usuarioExistente.setCiudad("Bogotá");

        when(usuarioRepositorio.findById(new ObjectId(id))).thenReturn(Optional.of(usuarioExistente));
        // Act
        usuarioServicio.editar(id, editarUsuarioDTO);
        // Assert
        verify(usuarioMapper).editarUsuarioDTO(editarUsuarioDTO, usuarioExistente);
        verify(usuarioRepositorio).save(usuarioExistente);
    }
    @Test
    void editarUsuarioConIdInvalidoDebeLanzarExcepcion() {
        // Arrange
        String idInvalido = "1234"; // No es un ObjectId válido
        EditarUsuarioDTO editarUsuarioDTO = new EditarUsuarioDTO(
                "Kevin Actualizado",
                "3100000000",
                "Cali"
        );
        // Act & Assert
        Exception exception = assertThrows(RecursoNoEncontradoException.class, () ->
                usuarioServicio.editar(idInvalido, editarUsuarioDTO)
        );
        assertEquals("El ID proporcionado no es válido: " + idInvalido, exception.getMessage());
        // Verificamos que no se haya llamado al repositorio ni al mapper
        verify(usuarioRepositorio, never()).findById(any());
        verify(usuarioMapper, never()).editarUsuarioDTO(any(), any());
        verify(usuarioRepositorio, never()).save(any());
    }
    @Test
    void editarUsuarioNoExistenteDebeLanzarExcepcion() {
        // Arrange
        String idValido = new ObjectId().toString();
        EditarUsuarioDTO editarUsuarioDTO = new EditarUsuarioDTO(
                "Kevin Actualizado",
                "3100000000",
                "Cali"
        );
        // Configuración del mock: el repositorio no encuentra al usuario
        when(usuarioRepositorio.findById(new ObjectId(idValido))).thenReturn(Optional.empty());
        // Act & Assert
        Exception exception = assertThrows(RecursoNoEncontradoException.class, () ->
                usuarioServicio.editar(idValido, editarUsuarioDTO)
        );
        assertEquals("No se encontró el usuario con el id " + idValido, exception.getMessage());
        // Verificamos que no se haya llamado al mapper ni a save
        verify(usuarioMapper, never()).editarUsuarioDTO(any(), any());
        verify(usuarioRepositorio, never()).save(any());
    }
    //Teste de eliminar
    @Test
    void eliminarUsuarioExitosamente() throws Exception {
        // Arrange
        String idValido = new ObjectId().toString();

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(new ObjectId(idValido));
        usuarioExistente.setEstado(EstadoUsuario.ACTIVO); // Estado inicial
        // Mock: repositorio encuentra al usuario
        when(usuarioRepositorio.findById(new ObjectId(idValido)))
                .thenReturn(Optional.of(usuarioExistente));
        // Act
        usuarioServicio.eliminar(idValido);
        // Assert
        assertEquals(EstadoUsuario.ELIMINADO, usuarioExistente.getEstado());
        verify(usuarioRepositorio).save(usuarioExistente);
    }
    @Test
    void eliminarUsuarioConIdInvalidoDebeLanzarExcepcion() {
        // Arrange
        String idInvalido = "1234"; // No es un ObjectId válido¿
        // Act & Assert
        Exception exception = assertThrows(Exception.class, () ->
                usuarioServicio.eliminar(idInvalido)
        );
        assertEquals("ID inválido: " + idInvalido, exception.getMessage());
        // Verificamos que no se haya interactuado con el repositorio
        verify(usuarioRepositorio, never()).findById(any());
        verify(usuarioRepositorio, never()).save(any());
    }
    @Test
    void eliminarUsuarioNoExistenteDebeLanzarExcepcion() {
        // Arrange
        String idValido = new ObjectId().toString();
        // Mock: el repositorio no encuentra al usuario
        when(usuarioRepositorio.findById(new ObjectId(idValido)))
                .thenReturn(Optional.empty());
        // Act & Assert
        Exception exception = assertThrows(RecursoNoEncontradoException.class, () ->
                usuarioServicio.eliminar(idValido)
        );
        assertEquals("No se encontró el usuario con el id " + idValido, exception.getMessage());
        // Verificamos que no se intente guardar nada
        verify(usuarioRepositorio, never()).save(any());
    }
    @Test
    void obtenerUsuarioExitosamente() throws Exception {
        // Arrange
        String idValido = new ObjectId().toString();
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(new ObjectId(idValido));
        usuarioExistente.setNombre("Kevin");
        usuarioExistente.setEmail("kevin@example.com");
        usuarioExistente.setTelefono("3100000000");
        usuarioExistente.setCiudad("Cali");
        usuarioExistente.setEstado(EstadoUsuario.ACTIVO);
        usuarioExistente.setRol(Rol.CLIENTE); // 👈
        usuarioExistente.setFechaRegistro(String.valueOf(LocalDateTime.now()));
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                idValido,
                "Kevin",
                "kevin@example.com",
                "3100000000",
                "Cali",
                EstadoUsuario.ACTIVO.name(),
                "CLIENTE",
                usuarioExistente.getFechaRegistro().toString()
        );
        // Mock repositorio devuelve el usuario
        when(usuarioRepositorio.findById(new ObjectId(idValido)))
                .thenReturn(Optional.of(usuarioExistente));
        // Mock mapper convierte la entidad a DTO
        when(usuarioMapper.toDTO(usuarioExistente)).thenReturn(usuarioDTO);
        // Act
        UsuarioDTO resultado = usuarioServicio.obtener(idValido);
        // Assert
        assertNotNull(resultado);
        assertEquals(usuarioDTO, resultado);
        // Verificamos que se haya llamado al mapper
        verify(usuarioMapper).toDTO(usuarioExistente);
    }
    @Test
    void obtenerUsuarioConIdInvalidoDebeLanzarExcepcion() {
        // Arrange
        String idInvalido = "1234"; // No es un ObjectId válido
        // Act & Assert
        Exception exception = assertThrows(Exception.class, () ->
                usuarioServicio.obtener(idInvalido)
        );
        assertEquals("ID inválido: " + idInvalido, exception.getMessage());
        // Verificamos que no se haya llamado al repositorio ni al mapper
        verify(usuarioRepositorio, never()).findById(any());
        verify(usuarioMapper, never()).toDTO(any());
    }
    @Test
    void obtenerUsuarioNoExistenteDebeLanzarExcepcion() {
        //Arrange
        String idValido = new ObjectId().toString();

        when(usuarioRepositorio.findById(new ObjectId(idValido)))
            .thenReturn(Optional.empty());

        Exception exception = assertThrows(RecursoNoEncontradoException.class, () ->
                usuarioServicio.obtener(idValido));
        assertEquals("No se encontró el usuario con el id " + idValido, exception.getMessage());

        // Verificamos que no se haya llamado al mapper
        verify(usuarioMapper, never()).toDTO(any());
    }
    //Faltan los demas test CRUD BASICO LISTO
}
