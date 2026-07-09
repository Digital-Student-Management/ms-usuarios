package com.sistemaEscolar.ms_usuarios.models.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// DTO de respuesta para Funcionario y usuarios genéricos (ej. ADMIN).
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class FuncionarioResponseDTO extends UsuarioResponseDTO {
}
