package com.sistemaEscolar.ms_usuarios.models.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// DTO de respuesta para Estudiante. Incluye sus datos específicos además de los del usuario base.
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EstudianteResponseDTO extends UsuarioResponseDTO {

    private LocalDate fechaNacimientoEstudiante;
    private Long idApoderado;
    private Long idHojaVida;
    private Long idAntMedico;
    private Long idAntAcademico;
}
