package co.edu.uniquindio.proyecto.servicios;

public interface EmailServicio {
    
    void enviarEmail(String asunto, String mensaje, String destinatario);
}