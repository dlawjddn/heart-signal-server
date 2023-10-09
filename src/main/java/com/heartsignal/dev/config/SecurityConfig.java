package com.heartsignal.dev.config;


import com.heartsignal.dev.filter.JwtAuthenticationProcessingFilter;
import com.heartsignal.dev.handler.OAuth2LoginFailureHandler;
import com.heartsignal.dev.handler.OAuth2LoginSuccessHandler;
import com.heartsignal.dev.oauth.CustomAuthenticationEntryPoint;
import com.heartsignal.dev.service.jwt.JwtService;
import com.heartsignal.dev.service.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CorsFilter corsFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .sessionManagement(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(config -> config
                        .requestMatchers("/api/v1/users/additional").hasAnyRole("GUEST")
                        .requestMatchers("/api/v1/users/duplicate-nickname/**").hasAnyRole("GUEST")
                        .requestMatchers(
                                "/oauth2/authorization/kakao",
                                "/login/oauth2/code/kakao",
                                "/api/v1/auth/refresh",
                                "/api/v1/users/additional",
                                "/ws-connection/**").permitAll()
                        .requestMatchers("/api/v1/users/dummy").permitAll()
                        .anyRequest().authenticated()
                );

        /**
         *  소셜로그인
         */
        http
                .oauth2Login(config -> config
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                );

        http
                .addFilterBefore(jwtAuthenticationProcessingFilter, LogoutFilter.class);

        http
                .exceptionHandling(config -> config.authenticationEntryPoint(customAuthenticationEntryPoint));

        http
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class);

        return http.build();
    }
}
