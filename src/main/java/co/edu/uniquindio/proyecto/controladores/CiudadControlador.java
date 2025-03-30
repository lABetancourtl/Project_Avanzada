//package co.edu.uniquindio.proyecto.controladores;
//
//import co.edu.uniquindio.proyecto.dto.CiudadDTO;
//import co.edu.uniquindio.proyecto.servicios.CiudadServicio;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/ciudades")
//public class CiudadControlador {
//
//    private final CiudadServicio ciudadServicio;
//
//    // Listar todas las ciudades
//    @GetMapping
//    public ResponseEntity<List<CiudadDTO>> listar() {
//        List<CiudadDTO> ciudades = ciudadServicio.listar();
//        return ResponseEntity.ok(ciudades);
//    }
//}