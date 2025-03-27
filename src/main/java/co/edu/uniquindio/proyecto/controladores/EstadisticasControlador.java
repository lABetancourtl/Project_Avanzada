pa@Serviceckage co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.EstadisticaDTO;
import co.edu.uniquindio.proyecto.servicios.EstadisticasServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estadisticas")
public class EstadisticasControlador {

    private final EstadisticasServicio estadisticasServicio;

    // Obtener estadísticas por categoría
    @GetMapping("/categorias")
    public ResponseEntity<List<EstadisticaDTO>> obtenerPorCategoria(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<EstadisticaDTO> estadisticas = estadisticasServicio.obtenerPorCategoria(fechaInicio, fechaFin);
        return ResponseEntity.ok(estadisticas);
    }

    // Obtener estadísticas por ciudad
    @GetMapping("/ciudades")
    public ResponseEntity<List<EstadisticaDTO>> obtenerPorCiudad(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<EstadisticaDTO> estadisticas = estadisticasServicio.obtenerPorCiudad(fechaInicio, fechaFin);
        return ResponseEntity.ok(estadisticas);
    }

       // Obtener estadísticas generales
       @GetMapping("/resumen")
       public ResponseEntity<Map<String, Object>> obtenerResumen() {
           Map<String, Object> resumen = estadisticasServicio.obtenerResumen();
           return ResponseEntity.ok(resumen);
       }
   }