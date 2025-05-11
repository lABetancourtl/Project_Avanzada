package co.edu.uniquindio.proyecto.servicios.impl.mapbox;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

@Service
public class MapboxService {

    @Value("${mapbox.token}")
    private String accessToken;

    public String obtenerCiudad(double lat, double lon) {
        try {
            // Crear la URL para la consulta de Mapbox
            String urlStr = String.format(
                    Locale.US,
                    "https://api.mapbox.com/geocoding/v5/mapbox.places/%f,%f.json?access_token=%s&types=place",
                    lon, lat, accessToken
            );
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Leer la respuesta de la API
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Procesar la respuesta JSON
            String jsonResponse = response.toString();
            System.out.println("Respuesta de Mapbox: " + jsonResponse);  // Imprime la respuesta completa

            JSONObject json = new JSONObject(jsonResponse);
            JSONArray features = json.getJSONArray("features");

            if (features.length() == 0) {
                return "Ciudad desconocida";  // Si no hay resultados, devuelve un mensaje claro
            }

            // Buscar una ciudad dentro de Colombia
            for (int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONArray context = feature.getJSONArray("context");

                // Recorre el contexto para encontrar el país
                String ciudad = feature.getString("text");
                for (int j = 0; j < context.length(); j++) {
                    JSONObject contextItem = context.getJSONObject(j);
                    String country = contextItem.getString("text");
                    if (country.equals("Colombia")) {
                        return ciudad; // Devuelve la ciudad en Colombia
                    }
                }
            }

            return "Ciudad desconocida"; // Si no se encontró ninguna ciudad válida en Colombia

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener ciudad"; // Error genérico si falla la conexión o el análisis
        }
    }
}
