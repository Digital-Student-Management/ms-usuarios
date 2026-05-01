package com.sistemaEscolar.ms_usuarios.models.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** {@link Funcionario} con área/disciplina (especialidad). */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Docente extends Funcionario {

    @NotBlank
    private String especialidad;
}
