package co.edu.uniquindio.proyecto.dto;


import java.time.LocalDateTime;

import java.util.List;

public record ReporteDTO(
        String id,
        String titulo,
        String descripcion,
        String estado,
        LocalDateTime fecha,
        boolean importante,
        String clienteId,
        String nombreCliente,
        UbicacionDTO ubicacion,
        String categoriaId,
        String nombreCategoria,
        List<String> fotos
    ) {

}