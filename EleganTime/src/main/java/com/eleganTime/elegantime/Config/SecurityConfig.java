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
                                .requestMatchers("/login", "/signup", "/public/**", "/css/**", "/js/**", "/img/**", "/img-site/**", "/home", "/cadastrarCliente", "/carrinho/**")
                                .permitAll()  // Permite acesso para todos, inclusive clientes

                                // Restringe o acesso a áreas específicas
                                .requestMatchers("/areaAdmin", "/listarUsuarios")
                                .hasRole("ADMINISTRADOR")

                                // Permite acesso para apenas estoquistas
                                .requestMatchers("/areaEstoquista")
                                .hasRole("ESTOQUISTA")

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

                                    // Verifica se o usuário tem a role de Admin ou Estoquista
                                    boolean isAdmin = authentication.getAuthorities().stream()
                                            .anyMatch(role -> role.getAuthority().equalsIgnoreCase("ROLE_ADMINISTRADOR"));
                                    boolean isEstoquista = authentication.getAuthorities().stream()
                                            .anyMatch(role -> role.getAuthority().equalsIgnoreCase("ROLE_ESTOQUISTA"));

                                    // Redireciona para a página correta dependendo da role
                                    if (isAdmin) {
                                        response.sendRedirect("/areaAdmin");  // Redireciona para a área Admin
                                    } else if (isEstoquista) {
                                        response.sendRedirect("/areaEstoquista");  // Redireciona para a área Estoquista
                                    } else {
                                        response.sendRedirect("/home");  // Redireciona para o home
                                    }
                                })
                )
                .logout(logout -> logout.permitAll());  // Permite que qualquer usuário faça logout

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Usando o customUserDetailsService e NoOpPasswordEncoder para comparação de senhas sem encriptação
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());  // Configura o NoOpPasswordEncoder

        return authenticationManagerBuilder.build();
    }
}
