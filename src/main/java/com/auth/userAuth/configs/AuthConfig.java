package com.auth.userAuth.configs;

import com.auth.userAuth.filters.JwtTokenVerificationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.auth.userAuth.entities.enums.Roles.ADMIN;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthConfig {
    private final JwtTokenVerificationFilter jwtTokenVerificationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/auth/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/test").hasRole(ADMIN.name())
                        .anyRequest().authenticated()
                )
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenVerificationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }
}
