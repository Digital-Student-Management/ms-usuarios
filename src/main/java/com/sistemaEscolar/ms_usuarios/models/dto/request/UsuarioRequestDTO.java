package com.sistemaEscolar.ms_usuarios.models.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO base abstracto para crear/actualizar cualquier tipo de usuario desde la API.
// "abstracto" significa que nunca se usará este molde directamente, 
// sino siempre uno de sus hijos (EstudianteRequestDTO, DocenteRequestDTO, etc.)
//
// @JsonTypeInfo y @JsonSubTypes reemplazaron estas anotaciones de la Entidad Usuario.java.
// Ahora la Entidad queda "pura" (solo le importa la BD), y este DTO maneja la deserialización del JSON.
//
// Cuando el frontend mande un POST a /api/usuarios, debe incluir el campo "tipoUsuario"
// para que Jackson sepa qué hijo instanciar. Ejemplo: { "tipoUsuario": "DOCENTE", ... }
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipoUsuario"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = EstudianteRequestDTO.class,  name = "ESTUDIANTE"),
    @JsonSubTypes.Type(value = ApoderadoRequestDTO.class,   name = "APODERADO"),
    @JsonSubTypes.Type(value = DocenteRequestDTO.class,     name = "DOCENTE"),
    @JsonSubTypes.Type(value = DirectivoRequestDTO.class,   name = "DIRECTIVO"),
    @JsonSubTypes.Type(value = InspectorRequestDTO.class,   name = "INSPECTOR")
})
@Data
@NoArgsConstructor
public abstract class UsuarioRequestDTO {

    // Campos comunes a TODOS los tipos de usuario (datos base de la persona)
    @NotBlank(message = "El RUT es obligatorio")
    private String rutUsuario;

    @NotBlank(message = "El primer nombre es obligatorio")
    private String pnombreUsuario;

    private String snombreUsuario; // Opcional

    @NotBlank(message = "El apellido paterno es obligatorio")
    private String appaternoUsuario;

    private String apmaternoUsuario; // Opcional

    @Email(message = "El correo debe tener formato válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correoUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasenaUsuario; // Solo llega en REQUEST, nunca se devuelve en RESPONSE

    @NotBlank(message = "El estado es obligatorio (ACTIVO / INACTIVO)")
    private String estadoUsuario;

    @NotNull(message = "El rol es obligatorio")
    private Long idRol; // El frontend llenará esto con el select de roles de GET /api/roles

    // Referencias a otros microservicios (opcionales en esta etapa)
    private Long idDireccion;
    private Long idComuna;
}
