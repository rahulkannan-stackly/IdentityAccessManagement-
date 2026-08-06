package com.techpalle.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {

        http.csrf(csrf -> csrf.disable()) .sessionManagement(session -> session.sessionCreationPolicy(
        		SessionCreationPolicy.STATELESS)).exceptionHandling(exception ->
        		exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))

                .authorizeHttpRequests(auth -> auth .requestMatchers( "/api/auth/register","/api/auth/logout",
                                "/api/auth/refresh", "/api/auth/forgot-password", "/api/auth/verify-otp",
                                "/api/auth/reset-password", "/swagger-ui/**","/swagger-ui.html",
                                "/v3/api-docs/**")
                .permitAll().anyRequest().authenticated() ).addFilterBefore(jwtAuthenticationFilter,
                        		UsernamePasswordAuthenticationFilter.class );return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager( AuthenticationConfiguration configuration)throws Exception {

        return configuration.getAuthenticationManager();
    }
}
