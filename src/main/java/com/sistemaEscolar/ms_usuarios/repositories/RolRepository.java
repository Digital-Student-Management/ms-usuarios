package com.sistemaEscolar.ms_usuarios.repositories;

import com.sistemaEscolar.ms_usuarios.models.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    // Este método devuelve TRUE si ya existe un registro con ese nombre.
    // Esto se usa para validar que no creemos dos veces el rol "ADMINISTRADOR", por ejemplo.
    boolean existsByNombreRol(String nombreRol);
}
