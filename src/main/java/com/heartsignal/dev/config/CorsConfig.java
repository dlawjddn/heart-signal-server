package com.heartsignal.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:3000",
                        "http://localhost:5173",
                        "http://heart.dcs-hyungjoon.com",
                        "https://heart.dcs-hyungjoon.com"
                )
        );
        configuration.setAllowedMethods(
                List.of("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
        );
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("DNT", "User-Agent", "X-Requested-With", "If-Modified-Since", "Cache-Control", "Content-Type", "Range","Set-Cookie"));
//        configuration.addExposedHeader("*");
        configuration.addExposedHeader("Set-Cookie");
        configuration.addExposedHeader("authorization");
        configuration.addExposedHeader("reauthorization");

        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
