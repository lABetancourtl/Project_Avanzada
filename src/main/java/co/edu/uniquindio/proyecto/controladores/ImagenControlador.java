package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.servicios.ImagenServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/imagenes")
public class ImagenControlador {

    private final ImagenServicio imagenServicio;

    @PostMapping(consumes = "multipart/form-data")
    public String subirImagen(MultipartFile arhcivo) throws Exception{
        Map datos = imagenServicio.subirImagen(arhcivo);
        return datos.toString();
    }

}