package co.edu.uniquindio.proyecto.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SeguridadSwaggerConfig {
//Pregunta al profesor si este paquete va
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/usuarios",
                                "/api/usuarios/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()) // forma moderna
                .csrf(csrf -> csrf.disable()) // forma moderna
                .build();
    }
}
