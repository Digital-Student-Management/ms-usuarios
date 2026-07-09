package com.sistemaEscolar.ms_usuarios.models.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// DTO de respuesta para Funcionario (y para usuarios genéricos como ADMIN).
// No agrega campos propios; solo hereda los datos base del usuario.
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class FuncionarioResponseDTO extends UsuarioResponseDTO {
}
