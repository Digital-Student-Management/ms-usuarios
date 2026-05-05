package com.sistemaEscolar.ms_usuarios.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// DTO para crear o actualizar un Directivo (ej: Director, Jefe UTP, etc.).
// El cargo define el rol institucional dentro del equipo de gestión del colegio.
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DirectivoRequestDTO extends UsuarioRequestDTO {

    @NotBlank(message = "El cargo directivo es obligatorio")
    private String cargoDirectivo;
}
