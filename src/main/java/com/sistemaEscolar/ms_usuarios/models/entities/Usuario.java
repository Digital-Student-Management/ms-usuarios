package com.sistemaEscolar.ms_usuarios.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
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

/** Persona/cuenta base del sistema; herencia JOINED con subtipo por rol. Contraseña: solo llega por JSON en alta/edición. */
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

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
