package com.sistemaEscolar.ms_usuarios.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * Generación y validación de tokens JWT firmados.
 */
@Component
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    public String generarToken(Long idUsuario, String rut, String rol) {
        Date ahora = new Date();
        Date exp = new Date(ahora.getTime() + expirationMs);
        return Jwts.builder()
                .subject(String.valueOf(idUsuario))
                .claims(Map.of("rut", rut == null ? "" : rut, "rol", rol == null ? "" : rol))
                .issuedAt(ahora)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    public Claims validarToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String obtenerRol(String token) {
        try {
            return validarToken(token).get("rol", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean esValido(String token) {
        try {
            validarToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
