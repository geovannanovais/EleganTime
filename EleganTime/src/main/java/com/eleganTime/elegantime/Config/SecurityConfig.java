package com.eleganTime.elegantime.Config;

import com.eleganTime.elegantime.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests

                                .requestMatchers("/login","produto/**", "/signup", "/public/**", "/css/**", "/js/**", "/img/**", "/img-site/**", "/home", "/cadastrarCliente", "/carrinho/**","/produto/**", "/adicionar/**")  // Adicionando '/adicionar/**' para permitir adicionar ao carrinho
                                .permitAll()  // Permite acesso para todos sem autenticação

                                // Restringe o acesso para rotas administrativas
                                .requestMatchers("/areaAdmin", "/listarUsuarios")
                                .hasRole("ADMINISTRADOR")

                                // Permite acesso para apenas estoquistas
                                .requestMatchers("/areaEstoquista")
                                .hasRole("ESTOQUISTA")

                                // Exige autenticação para outras rotas
                                .anyRequest().authenticated()  // Qualquer outra página exige autenticação
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")  // Página personalizada de login
                                .permitAll()  // Permite acesso à página de login
                                .successHandler((request, response, authentication) -> {
                                    // Debug para verificar as authorities do usuário
                                    authentication.getAuthorities().forEach(authority ->
                                            System.out.println("Authority: " + authority.getAuthority()));

                                    // Redireciona dependendo da role do usuário
                                    boolean isAdmin = authentication.getAuthorities().stream()
                                            .anyMatch(role -> role.getAuthority().equalsIgnoreCase("ROLE_ADMINISTRADOR"));
                                    boolean isEstoquista = authentication.getAuthorities().stream()
                                            .anyMatch(role -> role.getAuthority().equalsIgnoreCase("ROLE_ESTOQUISTA"));

                                    if (isAdmin) {
                                        response.sendRedirect("/areaAdmin");
                                    } else if (isEstoquista) {
                                        response.sendRedirect("/areaEstoquista");
                                    } else {
                                        response.sendRedirect("/areaCliente");
                                    }
                                })
                )
                .logout(logout -> logout.permitAll());  // Permite logout para todos

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Usando o customUserDetailsService e NoOpPasswordEncoder para comparação de senhas sem encriptação
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());  // Configura o NoOpPasswordEncoder para teste sem criptografia

        return authenticationManagerBuilder.build();
    }
}
