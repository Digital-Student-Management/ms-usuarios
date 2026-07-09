package com.sistemaEscolar.ms_usuarios.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

/**
 * JwtAuthFilter — Protege las operaciones sensibles de usuarios.
 *
 * Estrategia deliberada para NO romper el sistema:
 *   - Solo exige token en operaciones de ESCRITURA sobre /api/usuarios
 *     (POST, PUT, DELETE), que son las acciones dañinas.
 *   - Los GET quedan libres, porque varios microservicios consultan
 *     GET /api/usuarios/{id} de servidor a servidor SIN token.
 *   - Login y registro (/api/auth/**) quedan siempre públicos.
 *
 * En una segunda fase de endurecimiento debería añadirse autenticación
 * de servicio-a-servicio (API key interna) para proteger también los GET.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Set<String> METODOS_ESCRITURA = Set.of("POST", "PUT", "DELETE", "PATCH");
    private static final Set<String> ROLES_PERMITIDOS = Set.of("ADMIN", "DIRECTIVO");

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String metodo = request.getMethod();

        boolean esEscrituraDeUsuarios =
                path.startsWith("/api/usuarios") && METODOS_ESCRITURA.contains(metodo);

        if (!esEscrituraDeUsuarios) {
            chain.doFilter(request, response);
            return;
        }

        // A partir de aquí se exige un JWT válido con rol autorizado.
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            responder(response, HttpServletResponse.SC_UNAUTHORIZED, "Token de autenticación requerido");
            return;
        }

        String token = header.substring(7);
        Claims claims;
        try {
            claims = jwtService.validarToken(token);
        } catch (Exception e) {
            responder(response, HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
            return;
        }

        String rol = claims.get("rol", String.class);
        if (rol == null || !ROLES_PERMITIDOS.contains(rol.toUpperCase())) {
            responder(response, HttpServletResponse.SC_FORBIDDEN, "No tienes permisos para esta operación");
            return;
        }

        chain.doFilter(request, response);
    }

    private void responder(HttpServletResponse response, int status, String mensaje) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"status\":" + status + ",\"error\":\"" + mensaje + "\"}");
    }
}
