package com.sistemaEscolar.ms_usuarios.controller;

import com.sistemaEscolar.ms_usuarios.models.dto.request.RolRequestDTO;
import com.sistemaEscolar.ms_usuarios.models.dto.response.RolResponseDTO;
import com.sistemaEscolar.ms_usuarios.services.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST de Roles.
// Recibe RolRequestDTO y devuelve RolResponseDTO.
// El Panel de Administrador (frontend) usa GET /api/roles para llenar el dropdown de roles al crear usuarios.
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    // GET /api/roles → Lista todos los roles (id + nombre). Frontend los usa en el <select> de formularios.
    @GetMapping
    public List<RolResponseDTO> listar() {
        return rolService.listar();
    }

    // GET /api/roles/{id} → Un rol específico
    @GetMapping("/{id}")
    public RolResponseDTO obtenerPorId(@PathVariable Long id) {
        return rolService.obtenerPorId(id);
    }

    // POST /api/roles → Crea un rol nuevo. Ejemplo: { "nombreRol": "INSPECTOR_GENERAL" }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RolResponseDTO crear(@Valid @RequestBody RolRequestDTO dto) {
        return rolService.crear(dto);
    }

    // PUT /api/roles/{id} → Actualiza el nombre de un rol existente
    @PutMapping("/{id}")
    public RolResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody RolRequestDTO dto) {
        return rolService.actualizar(id, dto);
    }

    // DELETE /api/roles/{id} → Elimina un rol (responde 204 sin cuerpo)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
    }
}
