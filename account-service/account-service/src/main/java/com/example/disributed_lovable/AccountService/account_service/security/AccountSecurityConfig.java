package com.example.disributed_lovable.AccountService.account_service.security;

import com.example.disributed_lovable.CommonLib.common_lib.security.JwtAuthFilter;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AccountSecurityConfig
{

    private final JwtAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    {
        return httpSecurity
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/**","/webhooks/**","/swagger-ui/**","/swagger-ui.html",
                                "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/projects/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
