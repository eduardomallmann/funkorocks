package com.mcfadyen.shoppingcart.backend.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * Custom access denied handler.
 *
 * @author Eduardo Mallmann
 * @since 0.0.2
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request, 
                        jakarta.servlet.http.HttpServletResponse response, 
                        AccessDeniedException accessDeniedException) throws IOException {
        
        Authentication auth = null; // SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "anonymous";
        
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(String.format(
            "{\"error\": \"Access Denied\", \"message\": \"Access is denied for user: %s\", \"user\": \"%s\"}", 
            accessDeniedException.getMessage(),
            username
        ));
    }
}