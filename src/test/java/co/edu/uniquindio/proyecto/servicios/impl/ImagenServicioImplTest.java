package co.edu.uniquindio.proyecto.servicios.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ImagenServicioImplTest {

    @InjectMocks
    private ImagenServicioImpl imagenServicio;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Mockear cloudinary.uploader()
        when(cloudinary.uploader()).thenReturn(uploader);

        // Simular archivo temporal para la prueba
        when(multipartFile.getOriginalFilename()).thenReturn("testImage.jpg");
        when(multipartFile.getBytes()).thenReturn("fake image content".getBytes());

        // Inyectar manualmente el Cloudinary con mocks en el servicio (ya que el constructor lo crea internamente)
        Map<String, String> config = Map.of(
                "cloud_name", "fake",
                "api_key", "fake",
                "api_secret", "fake"
        );
        imagenServicio = new ImagenServicioImpl(
                config.get("cloud_name"),
                config.get("api_key"),
                config.get("api_secret")
        );

        // Reemplazar el uploader real por el mock
        var field = ImagenServicioImpl.class.getDeclaredField("cloudinary");
        field.setAccessible(true);
        field.set(imagenServicio, cloudinary);
    }

    @Test
    void testSubirImagenExitosamente() throws Exception {
        // Arrange
        Map<String, String> mockResponse = Map.of("url", "http://cloudinary.com/fake-image-url");
        when(uploader.upload(any(File.class), anyMap())).thenReturn(mockResponse);

        // Act
        Map result = imagenServicio.subirImagen(multipartFile);

        // Assert
        assertEquals(mockResponse, result);
        verify(uploader, times(1)).upload(any(File.class), anyMap());
    }

    @Test
    void testEliminarImagenExitosamente() throws Exception {
        // Arrange
        Map<String, String> mockResponse = Map.of("result", "ok");
        when(uploader.destroy(anyString(), anyMap())).thenReturn(mockResponse);

        // Act
        Map result = imagenServicio.eliminarImagen("fakeImageId");

        // Assert
        assertEquals(mockResponse, result);
        verify(uploader, times(1)).destroy(eq("fakeImageId"), anyMap());
    }
}

