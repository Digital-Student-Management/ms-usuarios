package com.sistemaEscolar.ms_usuarios.models.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de respuesta para Rol. Incluye el ID para que el frontend pueda usarlo en formularios.
@Data
@NoArgsConstructor
public class RolResponseDTO {

    private Long id;
    private String nombreRol;
}
