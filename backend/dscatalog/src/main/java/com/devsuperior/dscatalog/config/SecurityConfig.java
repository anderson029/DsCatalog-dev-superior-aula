package com.devsuperior.dscatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests(authorizeRequests -> authorizeRequests
                            .requestMatchers("/categories/**").permitAll() // Permitir acesso público para rotas específicas
                            .anyRequest().authenticated() // Exige autenticação para outras rotas
            ).csrf(csrf -> csrf.disable()); // Desativa CSRF, se necessário

    return http.build();
  }
}

