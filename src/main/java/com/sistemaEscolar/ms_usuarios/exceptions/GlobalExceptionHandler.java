package com.sistemaEscolar.ms_usuarios.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Esta clase actúa como un escudo o interceptor global para toda la API REST.
// Atrapa las excepciones que ocurren en los controladores y las transforma en respuestas JSON limpias.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura errores de validación de datos (ej. cuando falla un @NotBlank o @Email en el JSON recibido).
    // Transforma la lista interna de errores de Java en un objeto JSON con clave:valor indicando qué campo falló.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Error de Validación");
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.put("message", errors);
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Captura errores lógicos de negocio que lanzamos manualmente en los Servicios (como en UsuarioService).
    // Por ejemplo: cuando buscamos un ID que no existe y lanzamos un ResponseStatusException(HttpStatus.NOT_FOUND).
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", ex.getStatusCode().value());
        response.put("error", ex.getReason() != null ? ex.getReason() : "Error en la petición");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, ex.getStatusCode());
    }
}
