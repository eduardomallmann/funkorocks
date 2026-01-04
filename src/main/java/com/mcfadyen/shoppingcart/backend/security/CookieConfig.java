package com.mcfadyen.shoppingcart.backend.security;

import org.springframework.boot.web.servlet.server.CookieSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for cookie serialization and HTTP-only cookie settings.
 *
 * @author Eduardo Mallmann
 * @since 0.0.2
 */
@Configuration
public class CookieConfig {

    /**
     * Configures cookie serializer for Spring Session.
     *
     * @return CookieSerializer with HTTP-only settings
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        CookieSerializer serializer = new CookieSerializer();
        
        // Use JSON serialization for session data
        serializer.setJsonName("sessionCookieSerializer");
        serializer.setCookieName("JSESSIONID");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        serializer.setUseHttpOnlyCookie(true);
        serializer.setUseSecureCookie(false); // Set to true in production
        serializer.setSameSite("Lax");
        
        return serializer;
    }
}