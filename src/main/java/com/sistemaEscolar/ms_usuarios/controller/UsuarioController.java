package com.sistemaEscolar.ms_usuarios.controller;

import com.sistemaEscolar.ms_usuarios.models.entities.Usuario;
import com.sistemaEscolar.ms_usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Capa REST: cada método responde a una ruta HTTP y delega en {@link UsuarioService}.
 * {@code @Valid} aplica las anotaciones de la entidad (ej. {@code @NotBlank}, {@code @Email}) al JSON entrante.
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /** GET /api/usuarios — Lista completa en JSON (200 por defecto). */
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    /** GET /api/usuarios/{id} — Un usuario por clave numérica. */
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    /** GET /api/usuarios/rut/{rutUsuario} — Mismo dato pero buscando por RUT (evita confundir con el id). */
    @GetMapping("/rut/{rutUsuario}")
    public Usuario obtenerPorRut(@PathVariable String rutUsuario) {
        return usuarioService.obtenerPorRut(rutUsuario);
    }

    /** POST /api/usuarios — Crea fila nueva; respuesta HTTP 201 (recurso creado). */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crear(@Valid @RequestBody Usuario usuario) {
        return usuarioService.crear(usuario);
    }

    /** PUT /api/usuarios/{id} — Sustituye datos del usuario con ese {@code id} (valida body igual que alta). */
    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return usuarioService.actualizar(id, usuario);
    }

    /** DELETE /api/usuarios/{id} — Borra; 204 sin cuerpo en la respuesta. */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}
