package com.sistemaEscolar.ms_usuarios.models.mappers;

import com.sistemaEscolar.ms_usuarios.models.dto.request.*;
import com.sistemaEscolar.ms_usuarios.models.dto.response.*;
import com.sistemaEscolar.ms_usuarios.models.entities.*;

// Mapper manual: traduce entre DTOs y Entidades para aislar la BD del exterior.
// Tiene dos responsabilidades:
//   1. toEntity(DTO) → convierte lo que llegó del frontend en una Entidad guardable en BD.
//   2. toResponse(Entidad) → convierte lo que trajo la BD en un DTO seguro para devolver al frontend.
//
// Usamos instanceof (pattern matching de Java 16+) para detectar el subtipo y copiar los campos extra.
public class UsuarioMapper {

    // Convierte un RequestDTO (lo que llega del frontend) a la Entidad correcta para guardar en BD.
    // Detecta el subtipo con instanceof y crea el hijo correcto (Estudiante, Docente, etc.)
    public static Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario entidad;

        if (dto instanceof EstudianteRequestDTO e) {
            Estudiante est = new Estudiante();
            est.setFechaNacimientoEstudiante(e.getFechaNacimientoEstudiante());
            est.setIdApoderado(e.getIdApoderado());
            est.setIdHojaVida(e.getIdHojaVida());
            est.setIdAntMedico(e.getIdAntMedico());
            est.setIdAntAcademico(e.getIdAntAcademico());
            entidad = est;

        } else if (dto instanceof ApoderadoRequestDTO a) {
            Apoderado apo = new Apoderado();
            apo.setTelefonoApoderado(a.getTelefonoApoderado());
            apo.setProfesionOficioApoderado(a.getProfesionOficioApoderado());
            apo.setParentescoEst(a.getParentescoEst());
            apo.setIdAntApoderado(a.getIdAntApoderado());
            entidad = apo;

        } else if (dto instanceof DocenteRequestDTO d) {
            Docente doc = new Docente();
            doc.setEspecialidad(d.getEspecialidad());
            entidad = doc;

        } else if (dto instanceof DirectivoRequestDTO dir) {
            Directivo directivo = new Directivo();
            directivo.setCargoDirectivo(dir.getCargoDirectivo());
            entidad = directivo;

        } else if (dto instanceof InspectorRequestDTO i) {
            Inspector inspector = new Inspector();
            inspector.setSectorAsignadoInspector(i.getSectorAsignadoInspector());
            inspector.setTurnoInspector(i.getTurnoInspector());
            entidad = inspector;

        } else {
            // Nunca debería llegar aquí gracias a @JsonSubTypes, pero por seguridad lanzamos error claro.
            throw new IllegalArgumentException("Tipo de usuario desconocido: " + dto.getClass().getSimpleName());
        }

        // Copiamos los campos comunes (base) a la entidad resultante
        mapearCamposBase(dto, entidad);
        return entidad;
    }

    // Copia los campos comunes del DTO base hacia la entidad.
    // Método privado de apoyo para no repetir el mismo código en cada bloque if.
    private static void mapearCamposBase(UsuarioRequestDTO dto, Usuario entidad) {
        entidad.setRutUsuario(dto.getRutUsuario());
        entidad.setPnombreUsuario(dto.getPnombreUsuario());
        entidad.setSnombreUsuario(dto.getSnombreUsuario());
        entidad.setAppaternoUsuario(dto.getAppaternoUsuario());
        entidad.setApmaternoUsuario(dto.getApmaternoUsuario());
        entidad.setCorreoUsuario(dto.getCorreoUsuario());
        entidad.setContrasenaUsuario(dto.getContrasenaUsuario());
        entidad.setEstadoUsuario(dto.getEstadoUsuario());
        entidad.setIdRol(dto.getIdRol());
        entidad.setIdDireccion(dto.getIdDireccion());
        entidad.setIdComuna(dto.getIdComuna());
    }

    // Convierte una Entidad (lo que vino de la BD) a un ResponseDTO seguro para devolver al frontend.
    // La contraseña NUNCA se copia aquí → es imposible que salga por la API.
    public static UsuarioResponseDTO toResponse(Usuario entidad) {
        UsuarioResponseDTO dto;

        if (entidad instanceof Estudiante e) {
            EstudianteResponseDTO est = new EstudianteResponseDTO();
            est.setFechaNacimientoEstudiante(e.getFechaNacimientoEstudiante());
            est.setIdApoderado(e.getIdApoderado());
            est.setIdHojaVida(e.getIdHojaVida());
            est.setIdAntMedico(e.getIdAntMedico());
            est.setIdAntAcademico(e.getIdAntAcademico());
            dto = est;

        } else if (entidad instanceof Apoderado a) {
            ApoderadoResponseDTO apo = new ApoderadoResponseDTO();
            apo.setTelefonoApoderado(a.getTelefonoApoderado());
            apo.setProfesionOficioApoderado(a.getProfesionOficioApoderado());
            apo.setParentescoEst(a.getParentescoEst());
            apo.setIdAntApoderado(a.getIdAntApoderado());
            dto = apo;

        } else if (entidad instanceof Docente d) {
            DocenteResponseDTO doc = new DocenteResponseDTO();
            doc.setEspecialidad(d.getEspecialidad());
            dto = doc;

        } else if (entidad instanceof Directivo dir) {
            DirectivoResponseDTO directivo = new DirectivoResponseDTO();
            directivo.setCargoDirectivo(dir.getCargoDirectivo());
            dto = directivo;

        } else if (entidad instanceof Inspector i) {
            InspectorResponseDTO inspector = new InspectorResponseDTO();
            inspector.setSectorAsignadoInspector(i.getSectorAsignadoInspector());
            inspector.setTurnoInspector(i.getTurnoInspector());
            dto = inspector;

        } else {
            // Funcionario y usuarios base genéricos (p.ej. ADMIN)
            dto = new FuncionarioResponseDTO();
        }

        // Copiamos los campos comunes (base) al DTO resultante
        mapearCamposBaseResponse(entidad, dto);
        return dto;
    }

    // Copia los campos comunes de la entidad al DTO de respuesta.
    // Nótese que contrasenaUsuario NO aparece aquí → seguridad garantizada.
    private static void mapearCamposBaseResponse(Usuario entidad, UsuarioResponseDTO dto) {
        dto.setId(entidad.getId());
        dto.setRutUsuario(entidad.getRutUsuario());
        dto.setPnombreUsuario(entidad.getPnombreUsuario());
        dto.setSnombreUsuario(entidad.getSnombreUsuario());
        dto.setAppaternoUsuario(entidad.getAppaternoUsuario());
        dto.setApmaternoUsuario(entidad.getApmaternoUsuario());
        dto.setCorreoUsuario(entidad.getCorreoUsuario());
        dto.setEstadoUsuario(entidad.getEstadoUsuario());
        dto.setIdRol(entidad.getIdRol());
        dto.setIdDireccion(entidad.getIdDireccion());
        dto.setIdComuna(entidad.getIdComuna());
    }
}
