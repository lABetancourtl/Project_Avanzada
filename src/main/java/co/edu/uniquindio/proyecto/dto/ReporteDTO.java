package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public record ReporteDTO (
        String id,
        String titulo,
        String descripcion,
        String estadoActual,
        LocalDateTime fecha,
        String clienteId,
        String nombreCliente,
        UbicacionDTO ubicacion,
        String categoriaId,
        String nombreCategoria,
        List<String> fotos
){}