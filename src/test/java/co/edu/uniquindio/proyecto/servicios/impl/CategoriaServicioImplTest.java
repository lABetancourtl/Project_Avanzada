package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.CrearCategoriaDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Categoria;
import co.edu.uniquindio.proyecto.repositorios.CategoriaRepositorio;
import co.edu.uniquindio.proyecto.mapper.CategoriaMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServicioImplTest {

    @InjectMocks
    private CategoriaServicioImpl categoriaServicio;

    @Mock
    private CategoriaRepositorio categoriaRepositorio;

    @Mock
    private CategoriaMapper categoriaMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    //Teste de crear
    @Test
    void crearCategoriaExitosamente() throws Exception {
        // Arrange
        CrearCategoriaDTO crearCategoriaDTO = new CrearCategoriaDTO("Acidente", "Nada");
        Categoria categoria = new Categoria();
        categoria.setNombre(crearCategoriaDTO.nombre());
        // Mock: no existe categoría con ese nombre
        when(categoriaRepositorio.existsByNombreIgnoreCase(crearCategoriaDTO.nombre()))
                .thenReturn(false);
        // Mock: mapper convierte DTO a entidad
        when(categoriaMapper.toDocument(crearCategoriaDTO)).thenReturn(categoria);
        // Act
        categoriaServicio.crearCategoria(crearCategoriaDTO);
        // Assert
        verify(categoriaRepositorio).save(categoria);
        verify(categoriaMapper).toDocument(crearCategoriaDTO);
    }
    @Test
    void crearCategoriaConNombreDuplicadoDebeLanzarExcepcion() {
        // Arrange
        CrearCategoriaDTO crearCategoriaDTO = new CrearCategoriaDTO("Acidente","Cxd");
        // Mock: ya existe una categoría con ese nombre
        when(categoriaRepositorio.existsByNombreIgnoreCase(crearCategoriaDTO.nombre()))
                .thenReturn(true);
        // Act & Assert
        Exception exception = assertThrows(Exception.class, () ->
                categoriaServicio.crearCategoria(crearCategoriaDTO)
        );
        assertEquals("Ya existe una categoría con el nombre: " + crearCategoriaDTO.nombre(), exception.getMessage());
        // Verificamos que no se haya llamado al mapper ni a guardar
        verify(categoriaMapper, never()).toDocument(any());
        verify(categoriaRepositorio, never()).save(any());
    }
    //Teste de editar
    @Test
    void editarCategoriaExitosamente() throws Exception {
        // Arrange
        String idValido = new ObjectId().toString();
        CrearCategoriaDTO categoriaDTO = new CrearCategoriaDTO(
                "Hogar", // Nuevo nombre
                "icono-hogar.png" // Nuevo icono
        );
        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(new ObjectId(idValido));
        categoriaExistente.setNombre("Casa");
        categoriaExistente.setIcono("icono-casa.png");
        // Mock: la categoría existe por ID
        when(categoriaRepositorio.findById(new ObjectId(idValido)))
                .thenReturn(Optional.of(categoriaExistente));
        // Mock: no existe otra categoría con el mismo nombre
        when(categoriaRepositorio.existsByNombreIgnoreCase(categoriaDTO.nombre()))
                .thenReturn(false);
        // Act
        categoriaServicio.editarCategoria(idValido, categoriaDTO);
        // Assert
        // Verificamos que se actualicen los campos
        assertEquals(categoriaDTO.nombre(), categoriaExistente.getNombre());
        assertEquals(categoriaDTO.icono(), categoriaExistente.getIcono());
        // Verificamos que se guarde la categoría actualizada
        verify(categoriaRepositorio).save(categoriaExistente);
    }
    @Test
    void editarCategoriaNoExistenteDebeLanzarExcepcion() {
        // Arrange
        String idValido = new ObjectId().toString();
        CrearCategoriaDTO categoriaDTO = new CrearCategoriaDTO("Policia", "icono-policia.png");
        // Mock: la categoría no existe por ID
        when(categoriaRepositorio.findById(new ObjectId(idValido)))
                .thenReturn(Optional.empty());
        // Act & Assert
        Exception exception = assertThrows(Exception.class, () ->
                categoriaServicio.editarCategoria(idValido, categoriaDTO)
        );
        assertEquals("La categoría con ID " + idValido + " no existe", exception.getMessage());
        // Verificamos que no se haya hecho ninguna otra acción
        verify(categoriaRepositorio, never()).existsByNombreIgnoreCase(anyString());
        verify(categoriaRepositorio, never()).save(any());
    }
    @Test
    void editarCategoriaConNombreDuplicadoDebeLanzarExcepcion() {
        // Arrange
        String idValido = new ObjectId().toString();
        CrearCategoriaDTO categoriaDTO = new CrearCategoriaDTO(
                "Muertos", // Nombre que ya existe en otra categoría
                "icono-muertos.png"
        );
        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(new ObjectId(idValido));
        categoriaExistente.setNombre("Hogar"); // Nombre original diferente
        categoriaExistente.setIcono("icono-hogar.png");
        // Mock: la categoría existe por ID
        when(categoriaRepositorio.findById(new ObjectId(idValido)))
                .thenReturn(Optional.of(categoriaExistente));
        // Mock: existe otra categoría con el mismo nombre
        when(categoriaRepositorio.existsByNombreIgnoreCase(categoriaDTO.nombre()))
                .thenReturn(true);
        // Act & Assert
        Exception exception = assertThrows(Exception.class, () ->
                categoriaServicio.editarCategoria(idValido, categoriaDTO)
        );
        assertEquals("Ya existe una categoría con el nombre: " + categoriaDTO.nombre(), exception.getMessage());
        // Verificamos que no se guarde la categoría
        verify(categoriaRepositorio, never()).save(any());
    }
    //Teste de eliminar
    @Test
    void eliminarCategoriaExitosamente() throws Exception {
        // Arrange
        String idValido = new ObjectId().toString();
        ObjectId objectId = new ObjectId(idValido);
        // Mock: la categoría existe
        when(categoriaRepositorio.existsById(objectId)).thenReturn(true);
        // Act
        categoriaServicio.eliminarCategoria(idValido);
        // Assert
        verify(categoriaRepositorio).deleteById(objectId);
    }
    @Test
    void eliminarCategoriaNoExistenteDebeLanzarExcepcion() {
        // Arrange
        String idValido = new ObjectId().toString();
        ObjectId objectId = new ObjectId(idValido);
        // Mock: la categoría no existe
        when(categoriaRepositorio.existsById(objectId)).thenReturn(false);
        // Act & Assert
        Exception exception = assertThrows(Exception.class, () ->
                categoriaServicio.eliminarCategoria(idValido)
        );
        assertEquals("No se encontró una categoría con el ID: " + objectId, exception.getMessage());
        // Verificamos que no se intente eliminar
        verify(categoriaRepositorio, never()).deleteById(any());
    }
    //Teste de listar
    @Test
    void listarCategoriasExitosamente() {
        // Arrange
        ObjectId id1 = new ObjectId();
        ObjectId id2 = new ObjectId();

        Categoria categoria1 = new Categoria();
        categoria1.setId(id1);
        categoria1.setNombre("Tecnología");
        categoria1.setIcono("icono-tecnologia.png");

        Categoria categoria2 = new Categoria();
        categoria2.setId(id2);
        categoria2.setNombre("Hogar");
        categoria2.setIcono("icono-hogar.png");

        List<Categoria> categorias = List.of(categoria1, categoria2);
        CategoriaDTO categoriaDTO1 = new CategoriaDTO(
                id1,
                categoria1.getNombre(),
                categoria1.getIcono()
        );
        CategoriaDTO categoriaDTO2 = new CategoriaDTO(
                id2,
                categoria2.getNombre(),
                categoria2.getIcono()
        );
        // Mock: repositorio devuelve lista de categorías
        when(categoriaRepositorio.findAll()).thenReturn(categorias);
        // Mock: mapper convierte cada categoría a DTO
        when(categoriaMapper.toDTO(categoria1)).thenReturn(categoriaDTO1);
        when(categoriaMapper.toDTO(categoria2)).thenReturn(categoriaDTO2);
        // Act
        List<CategoriaDTO> resultado = categoriaServicio.listar();
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(categoriaDTO1));
        assertTrue(resultado.contains(categoriaDTO2));
        // Verificar que se haya llamado al repositorio y al mapper
        verify(categoriaRepositorio).findAll();
        verify(categoriaMapper).toDTO(categoria1);
        verify(categoriaMapper).toDTO(categoria2);
    }
    @Test
    void listarCategoriasListaVacia() {
        // Arrange
        when(categoriaRepositorio.findAll()).thenReturn(List.of()); // Repositorio devuelve lista vacía
        // Act
        List<CategoriaDTO> resultado = categoriaServicio.listar();
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(), "La lista de categorías debería estar vacía");
        // Verificar que se haya llamado al repositorio
        verify(categoriaRepositorio).findAll();
        // Verificar que no se haya llamado al mapper, ya que no hay entidades que mapear
        verify(categoriaMapper, never()).toDTO(any());
    }
}



