package com.lucasteixeira.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Instâncias de JwtUtil e UserDetailsService injetadas pelo Spring
    //private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    public static final String SECURITY_SCHEME = "bearerAuth";

    // Construtor para injeção de dependências de JwtUtil e UserDetailsService
    @Autowired
    public SecurityConfig(/*JwtUtil jwtUtil,*/ UserDetailsService userDetailsService) {
        //this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // Configuração do filtro de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/usuario").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios").permitAll()
                        .requestMatchers("/usuario/endereco/**").permitAll() // CEP continua aberto
                        .anyRequest().authenticated() // Outros exigem o token para preencher o Principal
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // Adiciona o filtro JWT antes do filtro de autenticação padrão

        // Retorna a configuração do filtro de segurança construída
        return http.build();
    }

    // Configura o PasswordEncoder para criptografar senhas usando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    // Configura o AuthenticationManager usando AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Obtém e retorna o AuthenticationManager da configuração de autenticação
        return authenticationConfiguration.getAuthenticationManager();
    }

}
