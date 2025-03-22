package co.edu.uniquindio.proyecto.dto;

public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
}
