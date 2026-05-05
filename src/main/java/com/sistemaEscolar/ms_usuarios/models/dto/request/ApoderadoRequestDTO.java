package com.sistemaEscolar.ms_usuarios.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// DTO para crear o actualizar un Apoderado.
// Hereda los campos base y agrega los datos específicos del apoderado.
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ApoderadoRequestDTO extends UsuarioRequestDTO {

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefonoApoderado;

    @NotBlank(message = "La profesión u oficio es obligatoria")
    private String profesionOficioApoderado;

    @NotBlank(message = "El parentesco con el estudiante es obligatorio")
    private String parentescoEst;

    // Referencia a los antecedentes del apoderado (otro MS o tabla futura)
    private Long idAntApoderado;
}
