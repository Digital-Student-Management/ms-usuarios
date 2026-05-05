package com.sistemaEscolar.ms_usuarios.models.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// Funcionario no tiene campos propios → solo necesita @NoArgsConstructor.
// @AllArgsConstructor se omite porque generaría un constructor idéntico al vacío (conflicto de compilación).
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Funcionario extends Usuario {
}
