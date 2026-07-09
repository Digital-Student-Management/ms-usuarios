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
 * JwtService — Generación y validación de tokens JWT firmados (HS256).
 *
 * Reemplaza el "token" simulado anterior (un string predecible sin firma).
 * El secreto y la expiración se leen de application.properties.
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

    /** Genera un JWT firmado con los datos del usuario (id, rut y rol). */
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

    /** Valida la firma y la expiración; devuelve los claims o lanza excepción si es inválido. */
    public Claims validarToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /** Extrae el rol del token (o null si es inválido). */
    public String obtenerRol(String token) {
        try {
            return validarToken(token).get("rol", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    /** true si el token tiene firma válida y no está expirado. */
    public boolean esValido(String token) {
        try {
            validarToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
