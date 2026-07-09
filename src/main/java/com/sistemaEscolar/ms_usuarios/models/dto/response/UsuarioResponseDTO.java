package com.sistemaEscolar.ms_usuarios.models.dto.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO base para DEVOLVER datos de un usuario al frontend o a otros microservicios.
// La diferencia clave con la Entidad y el RequestDTO:
//   - NO tiene el campo "contrasenaUsuario" → jamás sale la contraseña al exterior.
//   - SÍ tiene el campo "id" → el cliente necesita saber el ID para futuras peticiones.
//   - Incluye "tipoUsuario" en el JSON de respuesta → el frontend sabe qué tipo de usuario recibió.
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipoUsuario"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = EstudianteResponseDTO.class,  name = "ESTUDIANTE"),
    @JsonSubTypes.Type(value = ApoderadoResponseDTO.class,   name = "APODERADO"),
    @JsonSubTypes.Type(value = DocenteResponseDTO.class,     name = "DOCENTE"),
    @JsonSubTypes.Type(value = DirectivoResponseDTO.class,   name = "DIRECTIVO"),
    @JsonSubTypes.Type(value = InspectorResponseDTO.class,   name = "INSPECTOR"),
    @JsonSubTypes.Type(value = FuncionarioResponseDTO.class, name = "FUNCIONARIO")
})
@Data
@NoArgsConstructor
public abstract class UsuarioResponseDTO {

    private Long id;
    private String rutUsuario;
    private String pnombreUsuario;
    private String snombreUsuario;
    private String appaternoUsuario;
    private String apmaternoUsuario;
    private String correoUsuario;
    // contrasenaUsuario NO está aquí → seguridad garantizada por diseño
    private String estadoUsuario;
    private Long idRol;
    private Long idDireccion;
    private Long idComuna;
}
