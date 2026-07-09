# 🧑‍🏫 ms-usuarios — Usuarios y Autenticación

Microservicio de **autenticación, usuarios y roles** del Sistema de Gestión Escolar Digital (GED).
Es el servicio de seguridad del sistema: emite y valida los tokens JWT y almacena las cuentas.

> Parte del proyecto GED. Para ejecutar **todo el sistema**, ver el [README raíz](../README.md).

---

## ⚙️ Datos técnicos

| | |
|---|---|
| **Puerto** | `8089` |
| **Base de datos** | `ms_usuarios_db` (MySQL, se crea automáticamente) |
| **Stack** | Spring Boot 4 · Java 21 · Spring Data JPA · JJWT · Spring Security Crypto (BCrypt) · springdoc (Swagger) |

Modelo de usuarios con **herencia**: la entidad base `Usuario` y sus subtipos
`Estudiante`, `Docente`, `Apoderado`, `Directivo`, `Inspector` y `Funcionario`.

---

## 📡 Endpoints principales

### Autenticación — `/api/auth`
| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/auth/login` | Inicia sesión (por RUT o correo). Devuelve `{ token, user }` |
| POST | `/api/auth/register` | Registra un usuario. Público solo para **ESTUDIANTE/APODERADO** |

### Usuarios — `/api/usuarios`
| Método | Ruta | Descripción | Protección |
|--------|------|-------------|------------|
| GET | `/api/usuarios` | Lista todos los usuarios | Abierto |
| GET | `/api/usuarios/{id}` | Usuario por ID | Abierto |
| GET | `/api/usuarios/rut/{rut}` | Usuario por RUT | Abierto |
| POST | `/api/usuarios` | Crea un usuario | **JWT ADMIN/DIRECTIVO** |
| PUT | `/api/usuarios/{id}` | Actualiza un usuario | **JWT ADMIN/DIRECTIVO** |
| DELETE | `/api/usuarios/{id}` | Elimina un usuario | **JWT ADMIN/DIRECTIVO** |

También expone `/api/roles` para la gestión de roles.

---

## 🔒 Seguridad implementada

- **BCrypt**: las contraseñas se almacenan cifradas (nunca en texto plano). Al iniciar sesión con
  una contraseña antigua en texto plano, se re-guarda automáticamente cifrada (migración perezosa).
- **JWT firmado (HS512)** con expiración configurable. El secreto y la duración se definen en
  `application.properties` (`jwt.secret`, `jwt.expiration-ms`); en producción, usar la variable de
  entorno `JWT_SECRET`.
- **`JwtAuthFilter`**: exige token con rol **ADMIN/DIRECTIVO** para las operaciones de escritura de
  usuarios (POST/PUT/DELETE). Los GET quedan abiertos para permitir las consultas internas entre
  microservicios.
- **Prevención de escalada de privilegios**: el registro público solo acepta **ESTUDIANTE/APODERADO**;
  crear personal requiere un token de administrador (o que el sistema esté vacío, para el primer admin).

---

## ▶️ Ejecución

```bash
# Windows
mvnw.cmd spring-boot:run
# Linux / macOS
./mvnw spring-boot:run
```

- Documentación Swagger: **http://localhost:8089/swagger-ui.html**
- Requiere MySQL corriendo en `localhost:3306`.

---

## 🔗 Relación con otros servicios

Varios microservicios (cursos, estudiantes, murales, reuniones, mensajería) consultan
`GET /api/usuarios/{id}` para validar personas. Por eso este servicio debe estar **siempre activo**.
