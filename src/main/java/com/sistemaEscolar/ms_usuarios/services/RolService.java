package com.sistemaEscolar.ms_usuarios.services;

import com.sistemaEscolar.ms_usuarios.models.dto.request.RolRequestDTO;
import com.sistemaEscolar.ms_usuarios.models.dto.response.RolResponseDTO;
import com.sistemaEscolar.ms_usuarios.models.entities.Rol;
import com.sistemaEscolar.ms_usuarios.models.mappers.RolMapper;
import com.sistemaEscolar.ms_usuarios.repositories.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

// Servicio de Roles: ahora trabaja con DTOs.
// El frontend recibirá RolResponseDTO (con ID) y enviará RolRequestDTO (solo nombre).
@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    // Devuelve todos los roles como ResponseDTO.
    // ¿Para qué sirve?: El frontend llama a GET /api/roles para llenar el <select> de roles
    // al momento de crear un usuario en el Panel de Administrador.
    public List<RolResponseDTO> listar() {
        return rolRepository.findAll()
                .stream()
                .map(RolMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Obtiene un rol específico por ID y lo devuelve como ResponseDTO.
    public RolResponseDTO obtenerPorId(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
        return RolMapper.toResponse(rol);
    }

    // Crea un nuevo rol a partir del RequestDTO.
    // Valida que no exista ya un rol con el mismo nombre antes de guardar.
    @Transactional
    public RolResponseDTO crear(RolRequestDTO dto) {
        if (rolRepository.existsByNombreRol(dto.getNombreRol())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El rol ya existe");
        }
        Rol rol = RolMapper.toEntity(dto);
        return RolMapper.toResponse(rolRepository.save(rol));
    }

    // Actualiza el nombre de un rol existente.
    @Transactional
    public RolResponseDTO actualizar(Long id, RolRequestDTO dto) {
        Rol existente = rolRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
        if (!existente.getNombreRol().equals(dto.getNombreRol())
                && rolRepository.existsByNombreRol(dto.getNombreRol())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de rol ya está en uso");
        }
        existente.setNombreRol(dto.getNombreRol());
        return RolMapper.toResponse(rolRepository.save(existente));
    }

    // Elimina un rol. Si no existe, responde con 404.
    @Transactional
    public void eliminar(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado");
        }
        rolRepository.deleteById(id);
    }
}
