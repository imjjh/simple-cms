package com.malgn.configure.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {

        http.authorizeHttpRequests(
                request -> request
                        .requestMatchers(HttpMethod.POST, "/api/v1/members").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/contents/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated());

        http.formLogin(form -> form
                .loginProcessingUrl("/api/v1/auth/login")
                .successHandler(((request, response, authentication) -> response.setStatus(HttpStatus.OK.value())))
                .failureHandler(
                        ((request, response, exception) -> response.setStatus(HttpStatus.UNAUTHORIZED.value()))));

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
