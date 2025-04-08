package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.EmailDTO;

public interface EmailServicio {
    void enviarCorreo(EmailDTO emailDTO) throws Exception;
}
