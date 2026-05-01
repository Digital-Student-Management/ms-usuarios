package com.sistemaEscolar.ms_usuarios.models.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Subtipo de {@link Usuario}. En el modelo lógico solo aporta el subtipo "funcionario";
 * el {@code id} heredado actúa como identificador (equivalente a {@code id_funcionario} en el ER).
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario extends Usuario {
}
