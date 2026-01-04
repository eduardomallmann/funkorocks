package com.mcfadyen.shoppingcart.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Security configuration for OIDC/OAuth2 integration with Keycloak.
 *
 * @author Eduardo Mallmann
 * @since 0.0.2
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
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
            // OAuth2 resource server configuration
            .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
            )
            // Session management
            .sessionManagement(session -> session
                    .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED)
            )
            // CSRF protection
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/products/**", "/actuator/**", "/oauth2/**")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
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
     * @return CorsConfiguration
     */
    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Add Keycloak and frontend URLs
        config.addAllowedOrigin("http://localhost:8081");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        return config;
    }

    /**
     * Configures CORS filter bean.
     *
     * @param corsConfiguration the CORS configuration
     * @return CorsFilter
     */
    @Bean
    public org.springframework.web.filter.CorsFilter corsFilter(CorsConfiguration corsConfiguration) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new org.springframework.web.filter.CorsFilter(source);
    }

    /**
     * Custom authentication entry point for OAuth2 resource server.
     *
     * @return BearerTokenAuthenticationEntryPoint
     */
    @Bean
    public BearerTokenAuthenticationEntryPoint bearerTokenAuthenticationEntryPoint() {
        BearerTokenAuthenticationEntryPoint entryPoint = new BearerTokenAuthenticationEntryPoint();
        entryPoint.setRealmName("Funko Rocks");
        entryPoint.setErrorCode("UNAUTHORIZED");
        entryPoint.setErrorResponse((request, response, authException) -> {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Authentication required\"}");
            return null;
        });
        return entryPoint;
    }

    /**
     * Custom access denied handler.
     *
     * @return CustomAccessDeniedHandler
     */
    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    /**
     * Custom CSRF token repository.
     *
     * @return CookieCsrfTokenRepository
     */
    @Bean
    public CookieCsrfTokenRepository cookieCsrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }
}