package co.edu.eci.blueprints.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger para la documentación automática del API.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Define el bean de {@link OpenAPI} con metadatos básicos del servicio.
     * @return especificación OpenAPI
     */
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
            .info(new Info()
                .title("ARSW Blueprints API")
                .version("v2")
                .description("Blueprints Laboratory — Parte 2 con Seguridad JWT (Java 21 / Spring Boot 3.3.x)")
                .contact(new Contact()
                    .name("La Ley del Corazón")
                    .email("alison.calderrama-m@mail.escuelaing.edu.co"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
            )
            
            .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
            .components(new Components()
                .addSecuritySchemes("bearer-jwt",
                    new SecurityScheme()
                        .name("bearer-jwt")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}
