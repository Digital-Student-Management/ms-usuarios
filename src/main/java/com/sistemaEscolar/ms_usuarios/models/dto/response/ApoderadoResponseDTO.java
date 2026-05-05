package com.sistemaEscolar.ms_usuarios.models.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// DTO de respuesta para Apoderado.
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ApoderadoResponseDTO extends UsuarioResponseDTO {

    private String telefonoApoderado;
    private String profesionOficioApoderado;
    private String parentescoEst;
    private Long idAntApoderado;
}
