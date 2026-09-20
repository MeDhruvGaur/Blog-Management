package com.blogapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI blogOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog Management REST API")
                        .description("Comprehensive RESTful API for managing blog posts, categories, and comments with Spring Boot 3.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Blog API Development Team")
                                .email("support@blogapi.com")
                                .url("https://github.com/blog-api"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation and Wiki")
                        .url("https://github.com/blog-api/docs"));
    }
}
