package com.itsecasia.example.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.itsecasia.example.security.jwt.AuthEntryPointJwt;
import com.itsecasia.example.security.jwt.AuthTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

	@Value("${springdoc.swagger-ui.enabled}")
	private boolean isSwaggerEnabled;

	private AuthEntryPointJwt unauthorizedHandler;

	private AuthTokenFilter authTokenFilter;

	public WebSecurityConfiguration(AuthEntryPointJwt authEntryPointJwt, AuthTokenFilter authTokenFilter) {
		this.unauthorizedHandler = authEntryPointJwt;
		this.authTokenFilter = authTokenFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// TODO: Add more public end point
		http
			.csrf(csrf -> csrf.disable())
			.cors(Customizer.withDefaults())
			.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
			.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class).authorizeHttpRequests(
				authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(
					"/error"
				).permitAll());

		if (isSwaggerEnabled) {
			http
				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(
					"/swagger-ui/**",
					"/v3/api-docs/**"
				).permitAll());
		} else {
			http
				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(
					"/swagger-ui/**",
					"/v3/api-docs/**"
				).denyAll());
		}

		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().permitAll());

		return http.build();
	}

}
