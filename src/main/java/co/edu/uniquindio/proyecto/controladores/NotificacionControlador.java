package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.NotificacionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notificaciones")
public class NotificacionControlador {



    // Obtener notificaciones de un usuario
//    @GetMapping("/usuario/{usuarioId}")
//    public ResponseEntity<List<NotificacionDTO>> listarPorUsuario(@PathVariable String usuarioId) {
//        List<NotificacionDTO> notificaciones = notificacionServicio.listarPorUsuario(usuarioId);
//        return ResponseEntity.ok(notificaciones);
//    }
//
//    // Marcar notificación como leída
//    @PutMapping("/{id}/leida")
//    public ResponseEntity<String> marcarLeida(@PathVariable String id) throws Exception {
//        notificacionServicio.marcarLeida(id);
//        return ResponseEntity.ok("Notificación marcada como leída");
//    }
//
//    // Marcar todas las notificaciones de un usuario como leídas
//    @PutMapping("/usuario/{usuarioId}/leidas")
//    public ResponseEntity<String> marcarTodasLeidas(@PathVariable String usuarioId) {
//        notificacionServicio.marcarTodasLeidas(usuarioId);
//        return ResponseEntity.ok("Todas las notificaciones marcadas como leídas");
//    }
}