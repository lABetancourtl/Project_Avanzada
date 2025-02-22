package co.edu.uniquindio.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saludo")
public class SaludoControlador {

    @GetMapping
    public String saludar() {
        return "Hola, bienvenido a la aplicacion";
    }

    @GetMapping("/{nombre}")
    public String saludarNombre(@PathVariable String nombre) {
        return "Hola %s, bienvenido a la aplicacion".formatted(nombre);
    }

}
