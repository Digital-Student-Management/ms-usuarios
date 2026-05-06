package com.sistemaEscolar.ms_usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Configuración global de CORS (Cross-Origin Resource Sharing).
// Sin esta clase, el navegador bloquea cualquier petición que venga de un origen distinto al del servidor.
// Ejemplo: si el frontend corre en localhost:3000 e intenta llamar a localhost:8080, el navegador lo bloquea.
// Esta clase le dice a Spring que acepte esas peticiones de forma controlada.
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        // Permite peticiones desde cualquier origen (en producción se restringe al dominio real)
                        .allowedOrigins("*")
                        // Métodos HTTP permitidos
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Permite cualquier cabecera (como Authorization, Content-Type, etc.)
                        .allowedHeaders("*");
            }
        };
    }
}
