package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.NotificacionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

class WebSocketNotificationServiceTest {

    @InjectMocks
    private WebSocketNotificationService webSocketNotificationService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNotificarClientes() {
        // Arrange
        NotificacionDTO notificacion = new NotificacionDTO(
                "Título de prueba",
                "Cuerpo de prueba",
                "topic-test"
        );

        // Act
        webSocketNotificationService.notificarClientes(notificacion);

        // Assert
        verify(messagingTemplate, times(1))
                .convertAndSend("/topic/reports", notificacion);
    }

    @Test
    void testNotificarClienteEspecifico() {
        // Arrange
        String userId = "user123";
        NotificacionDTO notificacion = new NotificacionDTO(
                "Título para cliente",
                "Cuerpo para cliente",
                "topic-cliente"
        );

        // Act
        webSocketNotificationService.notificarClienteEspecifico(userId, notificacion);

        // Assert
        verify(messagingTemplate, times(1))
                .convertAndSend("/topic/user/" + userId + "/reports", notificacion);
    }
}

