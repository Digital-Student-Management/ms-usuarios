package com.sistemaEscolar.ms_usuarios.repositories;

import com.sistemaEscolar.ms_usuarios.models.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    boolean existsByNombreRol(String nombreRol);
}
