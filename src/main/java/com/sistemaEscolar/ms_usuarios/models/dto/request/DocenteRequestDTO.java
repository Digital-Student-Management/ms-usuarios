package com.sistemaEscolar.ms_usuarios.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// DTO para crear o actualizar un Docente.
// Hereda los campos base y agrega la especialidad del docente (ej: "Matemáticas", "Historia").
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DocenteRequestDTO extends UsuarioRequestDTO {

    @NotBlank(message = "La especialidad del docente es obligatoria")
    private String especialidad;
}
