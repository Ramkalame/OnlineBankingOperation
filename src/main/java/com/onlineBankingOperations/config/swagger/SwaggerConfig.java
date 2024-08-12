package com.onlineBankingOperations.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Online Banking Operation Api",
                description = "This is Online Banking Operation Api Documentation",
                contact = @Contact(name = "Ramswarup Kalame", email = "ramkalame14082001@gmail.com"),
                termsOfService = "Terms and Services",
                version = "1.0"
        )
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "JWT_Spring_Security",
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
