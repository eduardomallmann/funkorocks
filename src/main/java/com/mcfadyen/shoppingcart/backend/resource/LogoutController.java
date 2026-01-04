package com.mcfadyen.shoppingcart.backend.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * Controller for handling logout operations with Keycloak SLO support.
 *
 * @author Eduardo Mallmann
 * @since 0.0.2
 */
@RestController
@RequestMapping("/logout")
public class LogoutController {

    /**
     * Handles logout request and redirects to Keycloak for Single Logout.
     *
     * @param jwt JWT token of the authenticated user
     * @return redirect response to Keycloak logout
     */
    @PostMapping
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Jwt jwt) {
        
        if (jwt != null) {
            String username = jwt.getClaim("preferred_username");
            
            // Keycloak logout URL with redirect
            String keycloakLogoutUrl = "http://localhost:8081/realms/funkorocks/protocol/openid-connect/logout";
            String postLogoutRedirectUri = "http://localhost:8080/";
            
            String logoutUrl = keycloakLogoutUrl + 
                "?post_logout_redirect_uri=" + postLogoutRedirectUri + 
                "&client_id=shoppingcart-service";
            
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(logoutUrl))
                    .build();
        }
        
        // If not authenticated, just redirect to home
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:8080/"))
                .build();
    }
}