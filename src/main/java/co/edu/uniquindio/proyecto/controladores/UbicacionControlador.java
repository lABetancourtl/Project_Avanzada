package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.UbicacionDTO;
import co.edu.uniquindio.proyecto.servicios.UbicacionServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ubicaciones")
public class UbicacionControlador {

    private final UbicacionServicio ubicacionServicio;

    // Obtener ubicaciones cercanas a un punto
    @GetMapping("/cercanas")
    public ResponseEntity<List<UbicacionDTO>> obtenerCercanas(
            @RequestParam double latitud,
            @RequestParam double longitud,
            @RequestParam(defaultValue = "5") double radioKm) {
        List<UbicacionDTO> ubicaciones = ubicacionServicio.obtenerCercanas(latitud, longitud, radioKm);
        return ResponseEntity.ok(ubicaciones);
    }
}