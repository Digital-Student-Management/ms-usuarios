package com.sistemaEscolar.ms_usuarios.models.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** {@link Funcionario} de inspección: sector territorial y turno. */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Inspector extends Funcionario {

    @NotBlank
    private String sectorAsignadoInspector;

    @NotBlank
    private String turnoInspector;
}
