package com.sistemaEscolar.ms_usuarios.models.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** Usuario apoderado; ids largos opcionales hacia otros MS o tablas relacionadas cuando existan. */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Apoderado extends Usuario {

    @NotBlank
    private String telefonoApoderado;

    @NotBlank
    private String profesionOficioApoderado;

    @NotBlank
    private String parentescoEst;

    /** Ej. expediente médico/psicosocial fuera del MS hasta integrarlo. */
    private Long idAntApoderado;
}
