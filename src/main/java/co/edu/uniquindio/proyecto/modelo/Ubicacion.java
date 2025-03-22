package co.edu.uniquindio.proyecto.modelo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Ubicacion {

    private double latitud;
    private double longitud;
}
