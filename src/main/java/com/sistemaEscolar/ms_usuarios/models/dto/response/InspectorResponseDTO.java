package com.sistemaEscolar.ms_usuarios.models.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// DTO de respuesta para Inspector.
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class InspectorResponseDTO extends UsuarioResponseDTO {

    private String sectorAsignadoInspector;
    private String turnoInspector;
}
