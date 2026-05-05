package com.sistemaEscolar.ms_usuarios.models.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// DTO de respuesta para Docente.
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DocenteResponseDTO extends UsuarioResponseDTO {

    private String especialidad;
}
