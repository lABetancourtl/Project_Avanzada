package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.configuracion.WeatherConfig;
import co.edu.uniquindio.proyecto.servicios.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherConfig weatherConfig;

    @Override
    public String getWeather(double lat, double lon) {
        String url = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric",
                weatherConfig.getUrl(), lat, lon, weatherConfig.getKey());

        return restTemplate.getForObject(url, String.class);
    }
}
