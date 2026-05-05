package com.sistemaEscolar.ms_usuarios.models.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// DTO para crear o actualizar un Estudiante.
// Hereda todos los campos base de UsuarioRequestDTO y agrega los específicos del alumno.
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EstudianteRequestDTO extends UsuarioRequestDTO {

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimientoEstudiante;

    // ID del apoderado que ya debe existir en el sistema (referencia interna dentro del mismo MS)
    private Long idApoderado;

    // Referencias a otros microservicios (se conectarán via API cuando esos MS estén listos)
    private Long idHojaVida;
    private Long idAntMedico;
    private Long idAntAcademico;
}
