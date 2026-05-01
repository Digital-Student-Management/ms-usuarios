package com.sistemaEscolar.ms_usuarios.services;

import com.sistemaEscolar.ms_usuarios.models.entities.Usuario;
import com.sistemaEscolar.ms_usuarios.repositories.UsuarioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Reglas de negocio de usuarios: el controller solo recibe/envía HTTP; aquí está la lógica
 * contra la base ({@link UsuarioRepository}). Los errores lanzan HTTP vía {@link ResponseStatusException}.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /** Devuelve todos los registros de la tabla de usuarios. */
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    /** Busca por PK; si no existe, 404 para que el cliente sepa que no hay fila con ese id. */
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    /** Igual que por id, pero usando el RUT único definido en el repositorio. */
    public Usuario obtenerPorRut(String rutUsuario) {
        return usuarioRepository
                .findByRutUsuario(rutUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    /**
     * Alta: evita dos usuarios con el mismo RUT o correo. {@code id} null para que JPA cree fila nueva
     * (si viniera id del cliente podría intentar pisar otro registro).
     */
    @Transactional
    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByRutUsuario(usuario.getRutUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El RUT ya está registrado");
        }
        if (usuarioRepository.existsByCorreoUsuario(usuario.getCorreoUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        usuario.setId(null);
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza la fila que ya existe (mismo id): copia campos del body sobre la entidad cargada desde BD.
     * Si cambian RUT o correo, solo permite el cambio si no pertenecen ya a otro usuario (409 si chocan).
     */
    @Transactional
    public Usuario actualizar(Long id, Usuario datos) {
        Usuario existente = obtenerPorId(id);
        // El RUT cambió respecto al guardado: comprobar que el nuevo no esté usado por otro.
        if (!existente.getRutUsuario().equals(datos.getRutUsuario())
                && usuarioRepository.existsByRutUsuario(datos.getRutUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El RUT ya está registrado");
        }
        if (!existente.getCorreoUsuario().equals(datos.getCorreoUsuario())
                && usuarioRepository.existsByCorreoUsuario(datos.getCorreoUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        existente.setRutUsuario(datos.getRutUsuario());
        existente.setPnombreUsuario(datos.getPnombreUsuario());
        existente.setSnombreUsuario(datos.getSnombreUsuario());
        existente.setAppaternoUsuario(datos.getAppaternoUsuario());
        existente.setApmaternoUsuario(datos.getApmaternoUsuario());
        existente.setCorreoUsuario(datos.getCorreoUsuario());
        existente.setContrasenaUsuario(datos.getContrasenaUsuario());
        existente.setEstadoUsuario(datos.getEstadoUsuario());
        existente.setIdRol(datos.getIdRol());
        existente.setIdDireccion(datos.getIdDireccion());
        existente.setIdComuna(datos.getIdComuna());
        return usuarioRepository.save(existente);
    }

    /** Borra por id; si no existe el id, mismo criterio que consulta → 404. */
    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
