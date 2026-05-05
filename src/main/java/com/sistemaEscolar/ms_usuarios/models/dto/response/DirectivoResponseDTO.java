package com.sistemaEscolar.ms_usuarios.models.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// DTO de respuesta para Directivo.
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DirectivoResponseDTO extends UsuarioResponseDTO {

    private String cargoDirectivo;
}
