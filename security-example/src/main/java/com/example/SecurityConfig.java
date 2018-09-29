package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/foobar").hasRole("FOOBAR")
                .anyExchange().authenticated()
                .and()
                .httpBasic().and()
                .formLogin();
        return http.build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {

        final UserDetails user = User
                .withUsername("user")
                .password("password")
                .roles("USER")
                .passwordEncoder(passwordEncoder()::encode)
                .build();

        final UserDetails foobar = User
                .withUsername("foobar")
                .password("password")
                .roles("FOOBAR")
                .passwordEncoder(passwordEncoder()::encode)
                .build();

        return new MapReactiveUserDetailsService(user, foobar);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
