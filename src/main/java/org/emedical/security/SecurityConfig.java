package org.emedical.security;

import lombok.RequiredArgsConstructor;
import org.emedical.service.CustomUserDetailsService;
import org.emedical.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter, DaoAuthenticationProvider authProvider) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/doctors/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/doctors/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/doctors/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/doctors/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/doctors/*/appointments").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/doctors/appointments/*/start").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/doctors/queue").hasRole("DOCTOR")

                        .requestMatchers(HttpMethod.GET, "/api/nurses/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/nurses/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/nurses/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/nurses/*").hasRole("ADMIN")
                        .requestMatchers("/api/nurses/**").hasRole("NURSE")

                        .requestMatchers(HttpMethod.GET, "/api/teams/*").hasAnyRole("ADMIN", "DOCTOR", "NURSE")
                        .requestMatchers(HttpMethod.GET, "/api/teams/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/teams/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/teams/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/teams/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/appointment/**").hasAnyRole("DOCTOR", "NURSE")
                        .requestMatchers(HttpMethod.POST, "/api/appointment/**").hasRole("NURSE")
                        .requestMatchers(HttpMethod.PUT, "/api/appointment/**").hasAnyRole("NURSE")
                        .requestMatchers(HttpMethod.DELETE, "/api/appointment/**").hasRole("NURSE")

                        .requestMatchers(HttpMethod.GET, "/api/medical-record/**").hasAnyRole("DOCTOR", "NURSE")
                        .requestMatchers(HttpMethod.POST, "/api/medical-record/**").hasRole("DOCTOR")

                        .requestMatchers(HttpMethod.GET, "/api/patients/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/patients/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/patients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/patients/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/patients/team-patients").hasAnyRole("DOCTOR", "NURSE", "ADMIN")

                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
