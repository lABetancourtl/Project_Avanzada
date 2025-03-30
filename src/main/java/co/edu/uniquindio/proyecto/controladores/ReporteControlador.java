//package co.edu.uniquindio.proyecto.controladores;
//
//import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
//import co.edu.uniquindio.proyecto.dto.EditarReporteDTO;
//import co.edu.uniquindio.proyecto.dto.ReporteDTO;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/reportes")
//public class ReporteControlador {
//
//    private final ReporteServicio reporteServicio;
//
//    // Crear un nuevo reporte
//    @PostMapping
//    public ResponseEntity<String> crear(@Valid @RequestBody CrearReporteDTO reporte) throws Exception {
//        String id = reporteServicio.crear(reporte);
//        return ResponseEntity.ok("Reporte creado exitosamente con ID: " + id);
//    }
//
//    // Editar un reporte existente
//    @PutMapping("/{id}")
//    public ResponseEntity<String> editar(@PathVariable String id, @Valid @RequestBody EditarReporteDTO reporte) throws Exception {
//        reporteServicio.editar(id, reporte);
//        return ResponseEntity.ok("Reporte actualizado correctamente");
//    }
//
//    // Eliminar un reporte
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> eliminar(@PathVariable String id) throws Exception {
//        reporteServicio.eliminar(id);
//        return ResponseEntity.ok("Reporte eliminado correctamente");
//    }
//
//    // Obtener un reporte específico
//    @GetMapping("/{id}")
//    public ResponseEntity<ReporteDTO> obtener(@PathVariable String id) throws Exception {
//        ReporteDTO reporte = reporteServicio.obtener(id);
//        return ResponseEntity.ok(reporte);
//    }
//
//    // Listar reportes con filtros opcionales
//    @GetMapping
//    public ResponseEntity<List<ReporteDTO>> listar(
//            @RequestParam(required = false) String titulo,
//            @RequestParam(required = false) String categoria,
//            @RequestParam(required = false) String estado,
//            @RequestParam(defaultValue = "0") int pagina
//    ) {
//        List<ReporteDTO> reportes = reporteServicio.listar(titulo, categoria, estado, pagina);
//        return ResponseEntity.ok(reportes);
//    }
//
//    // Marcar un reporte como importante
//    @PutMapping("/{id}/importante")
//    public ResponseEntity<String> marcarImportante(@PathVariable String id) throws Exception {
//        reporteServicio.marcarImportante(id);
//        return ResponseEntity.ok("Reporte marcado como importante");
//    }
//
//    // Cambiar el estado de un reporte
//    @PutMapping("/{id}/estado/{nuevoEstado}")
//    public ResponseEntity<String> cambiarEstado(@PathVariable String id, @PathVariable String nuevoEstado) throws Exception {
//        reporteServicio.cambiarEstado(id, nuevoEstado);
//        return ResponseEntity.ok("Estado del reporte actualizado a: " + nuevoEstado);
//    }
//}