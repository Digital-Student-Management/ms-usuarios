package com.sistemaEscolar.ms_usuarios.models.mappers;

import com.sistemaEscolar.ms_usuarios.models.dto.request.RolRequestDTO;
import com.sistemaEscolar.ms_usuarios.models.dto.response.RolResponseDTO;
import com.sistemaEscolar.ms_usuarios.models.entities.Rol;

// Mapper para Rol: convierte entre el DTO (lo que llega/sale por la API) y la Entidad (lo que va a la BD).
public class RolMapper {

    // Convierte el RequestDTO a Entidad para guardar en BD.
    public static Rol toEntity(RolRequestDTO dto) {
        Rol rol = new Rol();
        rol.setNombreRol(dto.getNombreRol());
        return rol;
    }

    // Convierte la Entidad de BD a ResponseDTO para devolver al frontend.
    // Incluye el ID para que el frontend pueda usarlo en el select de "Asignar Rol" al crear usuarios.
    public static RolResponseDTO toResponse(Rol rol) {
        RolResponseDTO dto = new RolResponseDTO();
        dto.setId(rol.getId());
        dto.setNombreRol(rol.getNombreRol());
        return dto;
    }
}
