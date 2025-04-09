package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.servicios.EmailServicio;
import lombok.RequiredArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServicioImpl implements EmailServicio {

    private final Mailer mailer;

    @Value("${mail.smtp.username}")
    private String smtpUsername;

    @Override
    @Async
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {
        Email email = EmailBuilder.startingBlank()
                .from(smtpUsername)
                .to(emailDTO.destinatario())
                .withSubject(emailDTO.asunto())
                .withHTMLText(emailDTO.cuerpo())
                .buildEmail();

        mailer.sendMail(email);
    }
}
