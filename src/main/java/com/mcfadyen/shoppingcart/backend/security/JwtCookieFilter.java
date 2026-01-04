package com.mcfadyen.shoppingcart.backend.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT token storage filter for HTTP-only cookies.
 *
 * @author Eduardo Mallmann
 * @since 0.0.2
 */
public class JwtCookieFilter extends OncePerRequestFilter {

    private static final String JWT_COOKIE_NAME = "jwt_token";
    private static final int COOKIE_MAX_AGE = 1800; // 30 minutes

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, java.io.IOException {

        // Get JWT token from cookie
        String token = getTokenFromCookie(request);
        
        if (token != null && !token.isEmpty()) {
            // Create simple authentication for the token
            Authentication auth = new JwtAuthenticationToken(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extract JWT token from HTTP-only cookie.
     *
     * @param request HTTP request
     * @return JWT token or null
     */
    private String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Set JWT token in HTTP-only cookie.
     *
     * @param response HTTP response
     * @param token JWT token
     */
    public static void setTokenInCookie(HttpServletResponse response, String token) {
        Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); // Set to true in production
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(COOKIE_MAX_AGE);
        jwtCookie.setDomain("localhost"); // Adjust for your domain
        response.addCookie(jwtCookie);
    }

    /**
     * Clear JWT token cookie.
     *
     * @param response HTTP response
     */
    public static void clearTokenCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
    }

    /**
     * Simple JWT authentication token wrapper.
     */
    private static class JwtAuthenticationToken implements Authentication {
        private final String token;

        public JwtAuthenticationToken(String token) {
            this.token = token;
        }

        @Override
        public String getCredentials() {
            return token;
        }

        @Override
        public String getPrincipal() {
            return token;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public String getName() {
            return "jwt";
        }

        @Override
        public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
            return java.util.Collections.emptyList();
        }
    }
}