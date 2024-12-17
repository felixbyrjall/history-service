package com.github.felixbyrjall.historyservice.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Value("${jwt.secret-key}")
	private String base64EncodedKey;

	@Bean
	public SecretKey secretKey() {
		byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
		return Keys.hmacShaKeyFor(decodedKey);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				//.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.csrf(csrf -> csrf
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						.ignoringRequestMatchers(
								"/h2-console-history/**",
								"/actuator/health"
						)
				)
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/api/csrf").permitAll();
					auth.requestMatchers("/api/history/all").hasAuthority("ADMIN");
					auth.requestMatchers("/actuator/health").permitAll();  // Allow access to health endpoint
					auth.anyRequest().hasAuthority("USER");
				})
				.addFilterBefore(new JwtAuthenticationFilter(secretKey()),
						UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8000"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
		config.setAllowedHeaders(Arrays.asList(
				"Content-Type",
				"Authorization",
				"X-User-Id",
				"X-XSRF-TOKEN"
		));
		config.setExposedHeaders(Arrays.asList("X-XSRF-TOKEN"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}

