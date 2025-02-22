package co.edu.uniquindio.proyecto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController      // <------ indica que la clase será un controlador REST.
//@RequestMapping("/saludar")
public class SaludoControlador {

    //Metodo que responde a solicitudes GET
    @GetMapping("/salud")
    public String saludo() {
        return "Hi, welcome";
    }

    // @GetMapping("/{nombre}")   //de esta manera el usuario puede enviar informacion en la ruta HTTP  "/{nombre}" y etsa sera usada en el codigo
    // public String saludarNombre(@PathVariable String nombre) {
    //     return "Hola " + nombre;
    // }

    //Metodo que responde a solicitudes GET
        @GetMapping("/{nombre}")  //define que este metodo responde a las solicitudes GET en la URL /{nombre} donde {nombre} es un parámetro que se captura en la URL
        public String saludarNombre(@PathVariable("nombre") String nombre) {   //@PathVariable se utiliza para obtener el valor capturado en la URL y pasarlo al metodo como un parámetro
            return "Hola " + nombre;
    }

    //este ya no sirve, fue reemplazado por el SaludoControlador en el paquete controladores

}
