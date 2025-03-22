package co.edu.uniquindio.proyecto.modelo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class CodigoValidacion {

    private String codigo;
    private LocalDateTime fecha;
}
