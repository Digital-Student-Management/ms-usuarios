package com.sistemaEscolar.ms_usuarios.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// DTO para crear o actualizar un Inspector.
// El inspector tiene asignado un sector del colegio y un turno de trabajo.
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class InspectorRequestDTO extends UsuarioRequestDTO {

    @NotBlank(message = "El sector asignado es obligatorio")
    private String sectorAsignadoInspector;

    @NotBlank(message = "El turno del inspector es obligatorio")
    private String turnoInspector;
}
