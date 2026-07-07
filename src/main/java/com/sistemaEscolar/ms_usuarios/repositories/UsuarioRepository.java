package com.sistemaEscolar.ms_usuarios.repositories;

import com.sistemaEscolar.ms_usuarios.models.entities.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Acceso a datos de {@link Usuario}: extiende {@link JpaRepository} (CRUD estándar con {@code Long} como id).
 * Spring Data implementa esta interfaz y genera el SQL según el nombre del método ({@code findBy…}, {@code existsBy…}).
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /** Una fila por RUT único o vacío si no hay coincidencia. */
    Optional<Usuario> findByRutUsuario(String rutUsuario);

    /** Para validar alta/cambios sin cargar todo el objeto. */
    boolean existsByRutUsuario(String rutUsuario);

    /** Igual por correo único frente al negocio. */
    boolean existsByCorreoUsuario(String correoUsuario);

    /** Buscar por correo para el inicio de sesión */
    Optional<Usuario> findByCorreoUsuario(String correoUsuario);
}
