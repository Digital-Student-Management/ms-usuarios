package com.sistemaEscolar.ms_usuarios.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para crear o actualizar un Rol (ej: "DOCENTE", "ESTUDIANTE", "INSPECTOR").
// Es simple: solo necesita el nombre del rol.
@Data
@NoArgsConstructor
public class RolRequestDTO {

    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombreRol;
}
