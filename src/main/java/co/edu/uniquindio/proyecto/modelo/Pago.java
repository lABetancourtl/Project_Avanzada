package co.edu.uniquindio.proyecto.modelo;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class Pago {


    private LocalDateTime fecha;
    private float totalPagado;
    private String estado;
    private String metodoPago;


}
