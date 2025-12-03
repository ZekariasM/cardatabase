package com.packt.cardatabase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI carDatabaseOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Car REST API")
						.description("My car stock - Secured with JWT Authentication")
						.version("1.0"))
				.addSecurityItem(new SecurityRequirement()
						.addList("Bearer Authentication"))
				.components(new Components()
						.addSecuritySchemes("Bearer Authentication",
								new SecurityScheme()
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")
										.description("Enter JWT token (obtained from /login endpoint)")));
	}

}
