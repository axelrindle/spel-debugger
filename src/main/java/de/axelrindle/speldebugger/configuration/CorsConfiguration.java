package de.axelrindle.speldebugger.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * Allows CORS requests for local development when the Vite server runs standalone.
 */
@Configuration
@Profile("local")
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfig() {
        var methods = Arrays.stream(HttpMethod.values())
                .map(HttpMethod::name)
                .toArray(String[]::new);

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods(methods);
            }
        };
    }


}
