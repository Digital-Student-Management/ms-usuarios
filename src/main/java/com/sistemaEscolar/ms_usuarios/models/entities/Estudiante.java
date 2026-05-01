package com.sistemaEscolar.ms_usuarios.models.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** Usuario estudiante con datos académico-personales extras; misma tabla raíz {@link Usuario}. */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante extends Usuario {

    @NotNull
    private LocalDate fechaNacimientoEstudiante;

    /** FK lógico al mismo MS (usuario apoderado); sin JPA navegable. */
    private Long idApoderado;

    /** Referencias externas (otros MS); se resuelven por API cuando existan los servicios. */
    private Long idHojaVida;

    private Long idAntMedico;

    private Long idAntAcademico;
}
