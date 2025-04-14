package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.servicios.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor // Genera constructor con final fields
@RequestMapping("/api/weather")
public class WeatherControlador {

    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<String> getWeather(
            @RequestParam double lat,
            @RequestParam double lon) {
        String response = weatherService.getWeather(lat, lon);
        return ResponseEntity.ok(response);
    }
}
