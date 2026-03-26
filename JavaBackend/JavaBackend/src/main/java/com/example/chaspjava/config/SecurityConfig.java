package com.example.chaspjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;

// Although this class may appear unused in the IDE, it is automatically detected and applied
// by Spring Security at runtime via @Configuration and @Bean annotations. Spring Boot uses
// component scanning and reflection rather than explicit method calls to load security rules.
// Create Spring Security configuration class
@Configuration
public class SecurityConfig {
    // Defines the main Spring Security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection for REST API usage
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})  // Enable Cross-Origin Resource Sharing (CORS)
                .authorizeHttpRequests(auth -> auth // Configure request authorisation rules
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // Allow CORS preflight requests
                        .requestMatchers("/api/auth/**").permitAll()             // Register + Login
                        .anyRequest().permitAll()                                // Allow everything else
                )
                .formLogin(form -> form.disable())  // Disable default Spring login form
                .httpBasic(basic -> basic.disable()); // Disable HTTP Basic authentication
        // Build and return the security filter chain
        return http.build();
    }
    // Defines global CORS configuration for the application
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Allow frontend origin during development
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Allowed HTTP methods for cross-origin requests
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));  // Allow all request headers
        config.setAllowCredentials(true);  // Allow cookies and authentication headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Apply CORS configuration to all endpoints
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
