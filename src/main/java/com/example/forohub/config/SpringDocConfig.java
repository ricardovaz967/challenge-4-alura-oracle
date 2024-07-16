package com.example.forohub.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Foro Hub ONE")
                        .description("Es una API web que contiene métodos CRUD para temas, usuarios y respuestas. Los métodos CRUD permiten crear, leer, actualizar y eliminar información relacionada con estos elementos.")
                        .contact(new Contact()
                                .name("CJ Nieto")
                                .email(""))
                )
                .components(
                        new Components()
                        .addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")  // Asegúrate de que está configurado para coincidir con tus rutas de API
                .build();
    }
}

