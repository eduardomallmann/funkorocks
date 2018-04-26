package com.mcfadyen.shoppingcart.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Main class responsible to run the application.
 * <p>
 * It also implements the CORS filters to access the endpoints.
 * </p>
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
@SpringBootApplication
public class BackendApplication {


    /**
     * {@link javax.servlet.Filter} that handles CORS preflight requests and intercepts
     * CORS simple and actual requests.
     *
     * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     * @since 0.0.1
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("POST");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Main method responsible to run the application.
     * <p>
     * <p>Didn't use any arguments although needs to present it.</p>
     *
     * @param args arguments to be passed in the deploy.
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
