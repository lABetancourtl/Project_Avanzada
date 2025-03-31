package co.edu.uniquindio.proyecto.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoReporteDTO {

    private String id;

    private String titulo;

    private String descripcion;

    private String categoria;

    private String ciudad;

    private String estado;

    private LocalDateTime fechaCreacion;
}
