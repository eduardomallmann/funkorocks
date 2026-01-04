package com.mcfadyen.shoppingcart.backend.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * Custom authentication entry point for OAuth2 resource server.
 *
 * @author Eduardo Mallmann
 * @since 0.0.2
 */
public class BearerTokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String realmName;

    public BearerTokenAuthenticationEntryPoint(String realmName) {
        this.realmName = realmName;
    }

    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request, 
                        jakarta.servlet.http.HttpServletResponse response, 
                        AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(String.format(
            "{\"error\": \"Unauthorized\", \"message\": \"Authentication required\", \"realm\": \"%s\"}", 
            realmName
        ));
    }
}