package co.edu.uniquindio.proyecto.seguridad;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String claveSecreta;

    /**
     * Genera un token JWT con un mapa de claims personalizados.
     */
    public String generateToken(String id, Map<String, String> claims) {
        Instant now = Instant.now();

        return Jwts.builder()
                .claims(claims)
                .subject(id)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(1L, ChronoUnit.HOURS)))
                .signWith(getKey())
                .compact();
    }

    /**
     * Parsea y valida un JWT recibido, devolviendo los claims.
     */
    public Jws<Claims> parseJwt(String jwtString) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(getKey())
                .build();

        return jwtParser.parseSignedClaims(jwtString);
    }

    /**
     * Devuelve la clave secreta para firmar el JWT.
     */
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(claveSecreta.getBytes());
    }

}
