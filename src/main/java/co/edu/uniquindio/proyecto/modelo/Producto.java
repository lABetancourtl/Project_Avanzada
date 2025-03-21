package co.edu.uniquindio.proyecto.modelo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

import java.util.List;


@Document("productos")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producto {


    @Id
    @EqualsAndHashCode.Include
    private ObjectId codigo;


    private String nombre;

    private List<TipoProducto> tipoProductos;
    private int unidades;
    private float precio;
}
