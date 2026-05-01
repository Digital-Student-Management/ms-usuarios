# 🧑‍🏫 Microservicio de Usuarios (ms-usuarios)

Este repositorio contiene el código fuente del **Microservicio de Usuarios y Accesos**, parte integral de la arquitectura de microservicios híbrida del Sistema Integral de Gestión Estudiantil Digital. 

Este módulo actúa como el núcleo central para la gestión de identidades, perfiles y ubicaciones geográficas de toda la comunidad educativa.

---

## 📝 Descripción del Módulo

El `ms-usuarios` es responsable de administrar de forma segura y centralizada a todas las personas que interactúan con el sistema escolar. Centraliza la lógica de negocio para la creación, lectura, actualización y eliminación (CRUD) de perfiles, así como la gestión de roles y direcciones, evitando la redundancia de datos en otros microservicios.

### 📦 Entidades que gestiona (Bounded Context)
* **Usuarios y Perfiles:** `Usuario`, `Estudiante`, `Apoderado`, `Docente`, `Directivo`, `Inspector`, `Funcionario`.
* **Seguridad:** `Rol`.
* **Ubicación y Direcciones:** `Direccion`, `Comuna`, `Ciudad`, `Region`, `Pais`.

---

## 🛠️ Stack Tecnológico

El proyecto está construido bajo los estándares más modernos de la industria:

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 4.0.6
* **Gestor de dependencias:** Maven
* **Base de Datos:** MySQL (Local) / Supabase-PostgreSQL (Producción)
* **ORM:** Spring Data JPA / Hibernate
* **Seguridad:** Spring Security + JWT
* **Validaciones:** Spring Boot Validation
* **Comunicación:** Spring Cloud OpenFeign
* **Herramientas extra:** Lombok

---

## 🚀 Instalación y Ejecución en Entorno Local

Si deseas levantar este microservicio en tu máquina local para desarrollo, sigue estos pasos:

### 1. Clonar el repositorio
```bash
git clone [https://github.com/TuOrganizacion/ms-usuarios.git](https://github.com/TuOrganizacion/ms-usuarios.git)
cd ms-usuarios
