package com.sistemaEscolar.ms_usuarios.services;

import com.sistemaEscolar.ms_usuarios.models.dto.request.UsuarioRequestDTO;
import com.sistemaEscolar.ms_usuarios.models.dto.response.UsuarioResponseDTO;
import com.sistemaEscolar.ms_usuarios.models.entities.Usuario;
import com.sistemaEscolar.ms_usuarios.models.mappers.UsuarioMapper;
import com.sistemaEscolar.ms_usuarios.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

// Servicio de Usuarios: contiene la lógica de negocio.
// Ahora trabaja con DTOs en vez de Entidades, gracias al UsuarioMapper.
// El Controller solo ve DTOs. La BD solo ve Entidades. Este servicio hace la traducción.
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Lista todos los usuarios y los convierte a ResponseDTO (sin contraseñas).
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Busca por ID y devuelve el ResponseDTO del subtipo correcto (Estudiante, Docente, etc.)
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return UsuarioMapper.toResponse(usuario);
    }

    // Busca por RUT y devuelve el ResponseDTO correspondiente.
    public UsuarioResponseDTO obtenerPorRut(String rutUsuario) {
        Usuario usuario = usuarioRepository.findByRutUsuario(rutUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return UsuarioMapper.toResponse(usuario);
    }

    // Crea un nuevo usuario a partir del RequestDTO recibido.
    // El Mapper detecta el tipo (ESTUDIANTE, DOCENTE, etc.) y crea la entidad hija correcta.
    @Transactional
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByRutUsuario(dto.getRutUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El RUT ya está registrado");
        }
        if (usuarioRepository.existsByCorreoUsuario(dto.getCorreoUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        // El Mapper convierte el DTO → Entidad del tipo correcto (ej. DocenteRequestDTO → Docente)
        Usuario entidad = UsuarioMapper.toEntity(dto);
        entidad.setId(null); // Forzamos que JPA genere el ID (evita pisar registros existentes)
        Usuario guardado = usuarioRepository.save(entidad);
        return UsuarioMapper.toResponse(guardado);
    }

    // Actualiza los campos base de un usuario existente.
    // Nota: No se puede cambiar el "tipo" de un usuario (ej: no se puede convertir un Docente en Estudiante).
    @Transactional
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Validar que el nuevo RUT/correo no pertenezcan a otro usuario distinto
        if (!existente.getRutUsuario().equals(dto.getRutUsuario())
                && usuarioRepository.existsByRutUsuario(dto.getRutUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El RUT ya está registrado");
        }
        if (!existente.getCorreoUsuario().equals(dto.getCorreoUsuario())
                && usuarioRepository.existsByCorreoUsuario(dto.getCorreoUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        // Actualizamos solo los campos base (comunes a todos los tipos de usuario)
        existente.setRutUsuario(dto.getRutUsuario());
        existente.setPnombreUsuario(dto.getPnombreUsuario());
        existente.setSnombreUsuario(dto.getSnombreUsuario());
        existente.setAppaternoUsuario(dto.getAppaternoUsuario());
        existente.setApmaternoUsuario(dto.getApmaternoUsuario());
        existente.setCorreoUsuario(dto.getCorreoUsuario());
        existente.setContrasenaUsuario(dto.getContrasenaUsuario());
        existente.setEstadoUsuario(dto.getEstadoUsuario());
        existente.setIdRol(dto.getIdRol());
        existente.setIdDireccion(dto.getIdDireccion());
        existente.setIdComuna(dto.getIdComuna());

        return UsuarioMapper.toResponse(usuarioRepository.save(existente));
    }

    // Elimina un usuario por ID. Si no existe, responde con 404.
    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
