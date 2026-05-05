package com.sistemaEscolar.ms_usuarios.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad base del sistema: representa a cualquier persona con acceso al sistema.
// Usa herencia JOINED: cada subtipo (Docente, Estudiante, etc.) tiene su propia tabla en BD.
// Las anotaciones de Jackson (@JsonTypeInfo) fueron movidas a los DTOs → esta entidad queda limpia.
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String rutUsuario;

    @NotBlank
    private String pnombreUsuario;

    private String snombreUsuario;

    @NotBlank
    private String appaternoUsuario;

    private String apmaternoUsuario;

    @Email
    @NotBlank
    private String correoUsuario;

    // La contraseña se almacena en BD pero NUNCA se devuelve al exterior.
    // Esto se garantiza ahora por diseño en UsuarioResponseDTO (que no tiene este campo).
    @NotBlank
    private String contrasenaUsuario;

    @NotBlank
    private String estadoUsuario;

    /** Relación interna con {@link Rol} (referencia manual por id). */
    @NotNull
    private Long idRol;

    /** Referencias externas a otros microservicios (opcionales hasta integrar APIs). */
    private Long idDireccion;

    private Long idComuna;
}
