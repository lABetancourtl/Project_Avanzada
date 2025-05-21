package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.servicios.ImagenServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/imagenes")
public class ImagenControlador {

    private final ImagenServicio imagenServicio;

    @PostMapping()
    public ResponseEntity<String> subirImagen(@RequestParam("imagen") MultipartFile imagen) throws Exception {
        System.out.println("POST /api/imagenes recibido");
        Map datos = imagenServicio.subirImagen(imagen);
        String url = (String) datos.get("secure_url");
        return ResponseEntity.ok(url);
    }



}