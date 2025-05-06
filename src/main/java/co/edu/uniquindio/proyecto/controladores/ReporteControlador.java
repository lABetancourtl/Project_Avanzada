package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.servicios.ReporteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reportes")
public class ReporteControlador {

    private final ReporteServicio reporteServicio;

    // Crear un nuevo reporte
    @PostMapping
    public ResponseEntity<MensajeDTO<String>> crear(@Valid @RequestBody CrearReporteDTO reporte) throws Exception {
        reporteServicio.crearReporte(reporte);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Reporte creado exitosamente"));
    }

    // Editar un reporte existente
    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> editar(@PathVariable String id, @Valid @RequestBody EditarReporteDTO dto) throws Exception {
        reporteServicio.actualizarReporte(id, dto);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Reporte editado correctamente"));
    }

    // Eliminar un reporte
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) throws Exception {
        reporteServicio.eliminarReporte(id);
        return ResponseEntity.ok("Reporte eliminado correctamente");
    }

    /*
    // Obtener un reporte específico
    @GetMapping("/mis-reportes/{id}")
    public ResponseEntity<ReporteDTO> obtener(@PathVariable String id) throws Exception {
        ReporteDTO reporte = reporteServicio.obtener(id);
        return ResponseEntity.ok(reporte);
    }
     */

    @GetMapping("/mis-reportes/")
    public ResponseEntity<List<ReporteDTO>> obtener() throws Exception {
        List<ReporteDTO> misReportes = reporteServicio.obtener();
        return ResponseEntity.ok(misReportes);
    }

    @GetMapping("/reportes/categoria/{categoria}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorCategoria(@PathVariable String categoria) {
        List<ReporteDTO> reportes = reporteServicio.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(reportes);
    }


    // Marcar un reporte como importante
    @PutMapping("/{id}/importante")
    public ResponseEntity<String> marcarImportante(@PathVariable String id) throws Exception {
        reporteServicio.marcarImportante(id);
        return ResponseEntity.ok("Reporte marcado como importante");
    }

    //Cambiar estado de reporte
    @PutMapping("/api/reportes/{id}/estado")
    public ResponseEntity<?> cambiarEstadoReporte(@PathVariable String id, @RequestBody CambiarEstadoDTO cambiarEstadoDTO) throws Exception {
        reporteServicio.cambiarEstadoReporte(id, cambiarEstadoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Estado del reporte actualizado correctamente"));
    }

}