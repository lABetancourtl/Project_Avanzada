package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.servicios.EmailServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServicioImpl implements EmailServicio {

    private final JavaMailSender javaMailSender;
    
    @Override
    public void enviarEmail(String asunto, String mensaje, String destinatario) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        
        mailMessage.setFrom("tu_correo@gmail.com");
        mailMessage.setSubject(asunto);
        mailMessage.setText(mensaje);
        mailMessage.setTo(destinatario);
        
        javaMailSender.send(mailMessage);
    }
}