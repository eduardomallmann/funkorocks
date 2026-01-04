package com.mcfadyen.shoppingcart.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Security configuration for OIDC/OAuth2 integration with Keycloak.
 *
 * @author Eduardo Mallmann
 * @since 0.0.2
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity builder
     * @return configured SecurityFilterChain
     * @throws Exception configuration error
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                    // Public endpoints
                    .requestMatchers("/products/**", "/actuator/health", "/actuator/health/liveness", "/actuator/health/readiness").permitAll()
                    // Admin endpoints
                    .requestMatchers("/admin/**").hasRole("ADMINISTRATOR")
                    // All other endpoints require authentication
                    .anyRequest().hasRole("CUSTOMER")
            )
            // OAuth2 login configuration
            .oauth2Login(oauth2 -> oauth2
                    .loginPage("/oauth2/authorization/keycloak")
                    .defaultSuccessUrl("/shoppingcart", true)
            )
            // Session management
            .sessionManagement(session -> session
                    .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED)
            )
            // Exception handling
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
            )
            // Headers
            .headers(headers -> headers
                    .frameOptions().sameOrigin()
                    .contentTypeOptions()
            );
        
        return http.build();
    }

    /**
     * Configures JWT authentication converter.
     *
     * @return JwtAuthenticationConverter
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_");
        authoritiesConverter.setAuthoritiesClaimName("roles");
        
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        converter.setPrincipalClaimName("preferred_username");
        
        return converter;
    }

    /**
     * Configures CORS filter bean.
     *
     * @param corsConfiguration the CORS configuration
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Add Keycloak and frontend URLs
        config.addAllowedOrigin("http://localhost:8081");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}