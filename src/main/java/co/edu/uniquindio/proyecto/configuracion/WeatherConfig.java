package co.edu.uniquindio.proyecto.configuracion;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "openweather.api")
@Getter
@Setter
public class WeatherConfig {
    private String url;
    private String key;
}
