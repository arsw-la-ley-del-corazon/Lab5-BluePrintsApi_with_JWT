# Escuela Colombiana de Ingeniería Julio Garavito
## Arquitectura de Software – ARSW

[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Maven](https://img.shields.io/badge/Maven-3.9-blue.svg)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green.svg)](https://swagger.io/)
[![JWT](https://img.shields.io/badge/JWT-JSON%20Web%20Token-black.svg)](https://jwt.io/)

**Estudiantes:**
- [Alexandra Moreno](https://github.com/AlexandraMorenoL)
- [Alison Valderrama](https://github.com/LIZVALMU)
- [Jeisson Sánchez](https://github.com/JeissonS02)
- [Valentina Gutierrez](https://github.com/LauraGutierrezr)
---



## Requisitos
- **JDK 21**
- **Maven 3.9+**
- **Git**


---

## Ejecución del proyecto
1. Clonar o descomprimir el proyecto:
   ```bash
   git clone https://github.com/arsw-la-ley-del-corazon/Lab5-BluePrintsApi_with_JWT.git
   ```
   ó si el profesor entrega el `.zip`, descomprimirlo y entrar en la carpeta.

2. Ejecutar con Maven:
   ```bash
   mvn --projects Lab5-BluePrintsApi_with_JWT spring-boot:run
   ```

3. Verificar que la aplicación levante en `http://localhost:8080`.

---


## Actividades propuestas
   1. Revisar el código de configuración de seguridad (`SecurityConfig`) e identificar cómo se definen los endpoints públicos y protegidos.

   2. Explorar el flujo de login y analizar las claims del JWT emitido.

   3. Extender los scopes (`blueprints.read`, `blueprints.write`) para controlar otros endpoints de la API, del laboratorio P1 trabajado.
   
   4. Modificar el tiempo de expiración del token y observar el efecto.

   **RTA**
   
   En el codigo en el archivo `AuthController.java` se modifico la linea 37 de:
   ```java
       Instant exp = now.plusSeconds(60);
   ```
   a:
   ```java
       Instant exp = now.plusSeconds(ttl);
   ```
   Para poder comprobar como se comporta el sistema al expirar el token, no se validaba si el token expiraba, asi que se desarollo las debidas clases y metodos que permitieran validar el token en cada peticion, para esto se creo la clase `JwtValidationFilter.java` y se configuro en el archivo `SecurityConfig.java` para que se ejecutara antes de cada peticion.:
   
   ![Test Jwt Validations](/img/test-jwt-expired.png)


   5. Documentar en Swagger los endpoints de autenticación y de negocio.

---

<div align="center">
  <b>ECI-ARSW Team</b><br>
  <i>Empowering well-being through technology</i>
</div>
