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

   <img width="1313" height="395" alt="image" src="https://github.com/user-attachments/assets/99635fac-af66-40f9-9202-7028a4ed73f0" />

   - **Publico** 

   "/actuator/health", "/auth/login", /v3/api-docs/**, "/swagger-ui/**", "/swagger-ui.html". 
   
   La etiqueta `permitAll()` nos permite declarar excepciones de seguridad. 
   
   - **Protegido**
   
   /api/**.hasAnyAuthority("SCOPE_blueprints.read", "SCOPE_blueprints.write"): Esto nos indica que esta /api/** esta protegido y requiere permisos, ya que obliga a que el token JWT contenga al menos una de esas authorites. 
   
   El `anyRequest().authenticated()` nos obliga a que haya un usuario autenticado, es decir, que el request traiga un token JWT valido. 
         

   2. Explorar el flujo de login y analizar las claims del JWT emitido.

   En Swagger UI, ejecutamos el endpoint publico POST /auth/login con las credenciales de ejemplo: 
   Username : student 
   Password : student123   
   
   <img width="1248" height="588" alt="image" src="https://github.com/user-attachments/assets/4682cb76-6861-4bb3-aaee-6169f8c2e658" />
   
   Esto nos genera un objeto JSON con el token JWT: 
   
   <img width="1250" height="381" alt="image" src="https://github.com/user-attachments/assets/68b96d75-ca1f-409c-a653-40d5c35c7ef5" />
   
   Copiamos el valor de access_token en la opción Authorize de Swagger 
   
   <img width="1252" height="503" alt="image" src="https://github.com/user-attachments/assets/b643b93a-eca6-45ce-88e0-727467f8230c" />
   
   Una vez logrado, podemos invocar los endpoints protegidos por la API. 
   
   <img width="1193" height="783" alt="image" src="https://github.com/user-attachments/assets/522b07bd-82ac-41da-8675-819800af1af6" />
   

   Para analizar los claims del JWT emitido, hacemos uso de jwt.io, el cual descodifica el access_token 
   Nos otorga las siguientes claims:
   
   <img width="968" height="655" alt="image" src="https://github.com/user-attachments/assets/68be2ea4-ea58-43a3-8f11-30f777690e42" />
   
   
   - **Iss**: Identifica el emisor del token, en este caso nuestra API https://decsis-eci/blueprints.
   - **Sub**: Identifica el usuario autenticado que solicito el token, en este caso student. 
   - **Exp**: Es el tiempo de expiracion del token, en este caso Thu Sep 18 2025 20:17:07 GMT-0500. 
   - **Iat**: Se refiere al momento exacto en el que el token fue emitido: Thu Sep 18 2025 19:17:07 GMT-0500.
   - **Scope**: Define los permisos del usuario dentro del sistema, en este caso SCOPE_blueprints.read y SCOPE_blueprints.write.


   3. Extender los scopes (`blueprints.read`, `blueprints.write`) para controlar otros endpoints de la API, del laboratorio P1 trabajado.

   Para este punto importamos el proyecto trabajado en el lab P1 y le aplicamos la seguridad a los endpoints, para eso usamos `@PreAuthorize("hasAuthority('SCOPE_xxx')")`, esto nos dice que el token JWT solo funciona si incluye el scope, ya sea read o write 
   dependiendo de la solicitud del endpoint.
   
   <img width="1057" height="317" alt="image" src="https://github.com/user-attachments/assets/5c077b0f-0af7-4759-b88b-36e0436c1278" />


   
   4. Modificar el tiempo de expiración del token y observar el efecto.
   
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
