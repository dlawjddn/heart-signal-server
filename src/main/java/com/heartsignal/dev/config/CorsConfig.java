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
     /*   UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:3000",
//                        "http://localhost:5173",
                        "http://heart.dcs-hyungjoon.com",
                        "https://heart.dcs-hyungjoon.com"
                )
        );
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.setAllowedMethods(
                List.of("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
        );
//        configuration.setAllowedHeaders(Arrays.asList("DNT", "User-Agent", "X-Requested-With", "If-Modified-Since", "Cache-Control", "Content-Type", "Range","Set-Cookie"));
//        configuration.addExposedHeader("*");
        configuration.addExposedHeader("*");
        configuration.addExposedHeader("Set-Cookie");
        configuration.addExposedHeader("authorization");
        configuration.addExposedHeader("reauthorization");

        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);*/
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("https://heart.dcs-hyungjoon.com");

        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("PATCH");
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("authorization");
        configuration.addExposedHeader("reauthorization");

        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
