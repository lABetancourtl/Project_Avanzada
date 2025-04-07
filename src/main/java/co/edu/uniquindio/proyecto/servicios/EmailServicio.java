package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.EmailDTO;

public interface EmailServicio {
    void enviarEmail(EmailDTO emailDTO) throws Exception;
}
