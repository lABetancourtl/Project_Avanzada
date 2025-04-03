package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    private String id;
    private String titulo;
    private String descripcion;
    private String estado;
    private LocalDateTime fecha;
    private boolean importante;
    private String clienteId;
    private String nombreCliente;
    private UbicacionDTO ubicacion;
    private String categoriaId;
    private String nombreCategoria;
    private List<String> fotos;
}