package com.sistemaEscolar.ms_usuarios.controller;

import com.sistemaEscolar.ms_usuarios.models.entities.*;
import com.sistemaEscolar.ms_usuarios.models.dto.response.UsuarioResponseDTO;
import com.sistemaEscolar.ms_usuarios.models.mappers.UsuarioMapper;
import com.sistemaEscolar.ms_usuarios.repositories.UsuarioRepository;
import com.sistemaEscolar.ms_usuarios.repositories.RolRepository;
import com.sistemaEscolar.ms_usuarios.security.JwtService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final JwtService jwtService;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = null;
        
        // Buscar por RUT o por Correo
        if (request.getRut() != null && !request.getRut().isBlank()) {
            usuario = usuarioRepository.findByRutUsuario(request.getRut()).orElse(null);
        } else if (request.getEmail() != null && !request.getEmail().isBlank()) {
            usuario = usuarioRepository.findByCorreoUsuario(request.getEmail()).orElse(null);
        }

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no registrado");
        }

        // Verifica con BCrypt. Si la contraseña está en texto plano (datos antiguos),
        // se compara directo y se re-guarda hasheada tras un login exitoso.
        String almacenada = usuario.getContrasenaUsuario();
        boolean coincide;
        if (almacenada != null && almacenada.startsWith("$2")) {
            coincide = encoder.matches(request.getPassword(), almacenada);
        } else {
            coincide = almacenada != null && almacenada.equals(request.getPassword());
            if (coincide) {
                usuario.setContrasenaUsuario(encoder.encode(request.getPassword()));
                usuarioRepository.save(usuario);
            }
        }

        if (!coincide) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        // Obtener rol
        String roleName = "ESTUDIANTE"; // fallback
        Rol rol = rolRepository.findById(usuario.getIdRol()).orElse(null);
        if (rol != null) {
            roleName = rol.getNombreRol().toUpperCase();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtService.generarToken(usuario.getId(), usuario.getRutUsuario(), roleName));

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", usuario.getId());
        userMap.put("rut", usuario.getRutUsuario());
        userMap.put("nombre", usuario.getPnombreUsuario() + " " + usuario.getAppaternoUsuario());
        userMap.put("email", usuario.getCorreoUsuario());
        userMap.put("rol", roleName);
        
        response.put("user", userMap);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request,
                                      @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (usuarioRepository.existsByRutUsuario(request.getRut())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El RUT ya está registrado");
        }
        if (usuarioRepository.existsByCorreoUsuario(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        // El auto-registro público solo permite ESTUDIANTE/APODERADO. Crear personal requiere
        // un token de ADMIN/DIRECTIVO, salvo que el sistema esté vacío (primer administrador).
        String rolSolicitado = request.getRol() == null ? "" : request.getRol().trim().toUpperCase();
        boolean esRolPrivilegiado = !rolSolicitado.equals("ESTUDIANTE") && !rolSolicitado.equals("APODERADO");
        boolean sistemaVacio = usuarioRepository.count() == 0;

        if (esRolPrivilegiado && !sistemaVacio) {
            String rolSolicitante = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                rolSolicitante = jwtService.obtenerRol(authHeader.substring(7));
            }
            boolean autorizado = "ADMIN".equalsIgnoreCase(rolSolicitante)
                    || "DIRECTIVO".equalsIgnoreCase(rolSolicitante);
            if (!autorizado) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "El auto-registro solo permite Estudiante o Apoderado. Las cuentas de personal las crea un administrador.");
            }
        }

        // Buscar o crear rol
        Rol rol = rolRepository.findAll().stream()
                .filter(r -> r.getNombreRol().equalsIgnoreCase(request.getRol()))
                .findFirst()
                .orElseGet(() -> {
                    Rol newRol = new Rol();
                    newRol.setNombreRol(request.getRol().toUpperCase());
                    return rolRepository.save(newRol);
                });

        Usuario usuario;
        String roleStr = request.getRol().toUpperCase();
        
        // Separar nombre completo
        String[] nombreParts = request.getNombreCompleto().split("\\s+");
        String pnombre = nombreParts.length > 0 ? nombreParts[0] : "";
        String snombre = nombreParts.length > 2 ? nombreParts[1] : "";
        String appaterno = "";
        String apmaterno = "";
        
        if (nombreParts.length > 2) {
            appaterno = nombreParts[nombreParts.length - 2];
            apmaterno = nombreParts[nombreParts.length - 1];
        } else if (nombreParts.length == 2) {
            appaterno = nombreParts[1];
        } else {
            appaterno = "Registrado";
        }

        if (roleStr.equals("ESTUDIANTE")) {
            Estudiante est = new Estudiante();
            est.setFechaNacimientoEstudiante(java.time.LocalDate.now().minusYears(15));
            usuario = est;
        } else if (roleStr.equals("DOCENTE")) {
            Docente doc = new Docente();
            doc.setEspecialidad("General");
            usuario = doc;
        } else if (roleStr.equals("APODERADO")) {
            Apoderado apo = new Apoderado();
            apo.setTelefonoApoderado("+56999999999");
            apo.setProfesionOficioApoderado("General");
            apo.setParentescoEst("Apoderado");
            usuario = apo;
        } else if (roleStr.equals("INSPECTOR")) {
            Inspector ins = new Inspector();
            ins.setSectorAsignadoInspector("Patio General");
            ins.setTurnoInspector("Completo");
            usuario = ins;
        } else if (roleStr.equals("DIRECTIVO")) {
            Directivo dir = new Directivo();
            dir.setCargoDirectivo("Directivo");
            usuario = dir;
        } else {
            // ADMIN, FUNCIONARIO u otros roles genéricos se mapean a Funcionario.
            usuario = new Funcionario();
        }

        usuario.setRutUsuario(request.getRut());
        usuario.setPnombreUsuario(pnombre);
        usuario.setSnombreUsuario(snombre);
        usuario.setAppaternoUsuario(appaterno);
        usuario.setApmaternoUsuario(apmaterno);
        usuario.setCorreoUsuario(request.getEmail());
        usuario.setContrasenaUsuario(encoder.encode(request.getPassword()));
        usuario.setEstadoUsuario("ACTIVO");
        usuario.setIdRol(rol.getId());

        Usuario guardado = usuarioRepository.save(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtService.generarToken(guardado.getId(), guardado.getRutUsuario(), roleStr));

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", guardado.getId());
        userMap.put("rut", guardado.getRutUsuario());
        userMap.put("nombre", guardado.getPnombreUsuario() + " " + guardado.getAppaternoUsuario());
        userMap.put("email", guardado.getCorreoUsuario());
        userMap.put("rol", roleStr);
        
        response.put("user", userMap);

        return ResponseEntity.ok(response);
    }

    @Data
    public static class LoginRequest {
        private String rut;
        private String email;
        private String password;
    }

    @Data
    public static class RegisterRequest {
        private String nombreCompleto;
        private String rut;
        private String email;
        private String password;
        private String rol;
    }
}
