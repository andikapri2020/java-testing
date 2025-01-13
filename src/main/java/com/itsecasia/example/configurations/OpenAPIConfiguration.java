package com.itsecasia.example.configurations;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {

	private static final String SECURITY_SCHEMA_NAME = "Bearer Authentication";

	@Value("${app.name}")
	private String appName;

	@Value("${app.version}")
	private String appVersion;

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(info())
			.addSecurityItem(securityRequirement())
			.components(components())
			.servers(servers());
	}

	private List<Server> servers() {
		return List.of(
			new Server().url("/")
		);
	}

	private Info info() {
		return new Info()
			.title(appName)
			.version(appVersion)
			.description("API")
			.contact(contact())
			.license(license())
			.termsOfService("API TOSsss");
	}

	private Contact contact() {
		return new Contact()
			.name("ITSec Asia")
			.email("myeaddress@company.com")
			.url("www.itsecasia.com");
	}

	private License license() {
		return new License()
			.name("License of API")
			.url("API license URL");
	}

	private SecurityRequirement securityRequirement() {
		return new SecurityRequirement()
			.addList(SECURITY_SCHEMA_NAME);
	}

	private SecurityScheme securityScheme() {
		return new SecurityScheme()
			.name(SECURITY_SCHEMA_NAME)
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT");
	}

	private Components components() {
		return new Components()
			.addSecuritySchemes(SECURITY_SCHEMA_NAME, securityScheme());
	}
	
}
