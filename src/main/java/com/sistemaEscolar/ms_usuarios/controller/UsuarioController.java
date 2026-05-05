package com.sistemaEscolar.ms_usuarios.controller;

import com.sistemaEscolar.ms_usuarios.models.dto.request.UsuarioRequestDTO;
import com.sistemaEscolar.ms_usuarios.models.dto.response.UsuarioResponseDTO;
import com.sistemaEscolar.ms_usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST de Usuarios.
// Ahora recibe UsuarioRequestDTO (no entidades crudas) y devuelve UsuarioResponseDTO (sin contraseñas).
// El Controller no tiene lógica: solo recibe, delega al Service, y devuelve la respuesta.
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // GET /api/usuarios → Lista todos los usuarios (sin contraseñas)
    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioService.listar();
    }

    // GET /api/usuarios/{id} → Un usuario por su ID
    @GetMapping("/{id}")
    public UsuarioResponseDTO obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    // GET /api/usuarios/rut/{rut} → Un usuario por su RUT (útil para otros microservicios)
    @GetMapping("/rut/{rutUsuario}")
    public UsuarioResponseDTO obtenerPorRut(@PathVariable String rutUsuario) {
        return usuarioService.obtenerPorRut(rutUsuario);
    }

    // POST /api/usuarios → Crea un usuario. El JSON debe incluir "tipoUsuario" para el polimorfismo.
    // Ejemplo: { "tipoUsuario": "DOCENTE", "rutUsuario": "...", "especialidad": "Matemáticas", ... }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        return usuarioService.crear(dto);
    }

    // PUT /api/usuarios/{id} → Actualiza los datos base de un usuario existente
    @PutMapping("/{id}")
    public UsuarioResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto) {
        return usuarioService.actualizar(id, dto);
    }

    // DELETE /api/usuarios/{id} → Elimina un usuario (responde 204 sin cuerpo)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}
