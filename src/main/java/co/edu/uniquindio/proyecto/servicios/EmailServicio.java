package co.edu.uniquindio.proyecto.servicios;

import org.springframework.stereotype.Service;

@Service
public interface EmailServicio {
    
    void enviarEmail(String asunto, String mensaje, String destinatario);
}