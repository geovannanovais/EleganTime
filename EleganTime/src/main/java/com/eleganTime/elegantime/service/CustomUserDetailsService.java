package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Cliente;
import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.repository.ClienteRepository;
import com.eleganTime.elegantime.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório de Usuários

    @Autowired
    private ClienteRepository clienteRepository; // Repositório de Clientes

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username).orElse(null);

        if (usuario != null) {
            // Aqui, não é necessário adicionar "ROLE_" no papel, porque o Spring Security já vai fazer isso automaticamente
            return User.withUsername(usuario.getEmail())
                    .password("{noop}" + usuario.getSenha())  // Prefixa a senha com {noop} para indicar que não está criptografada
                    .roles(usuario.getGrupo().toUpperCase())  // Não adiciona "ROLE_" manualmente
                    .build();
        }

        Cliente cliente = clienteRepository.findByEmail(username).orElse(null);

        if (cliente != null) {
            // Para o cliente, o papel "CLIENTE" também não deve ter o prefixo "ROLE_"
            return User.withUsername(cliente.getEmail())
                    .password("{noop}" + cliente.getSenha())  // Prefixa a senha com {noop}
                    .roles("CLIENTE")  // Não adiciona "ROLE_" manualmente
                    .build();
        }

        // Se nem usuário nem cliente forem encontrados, lança exceção
        throw new UsernameNotFoundException("Usuário ou Cliente não encontrado com o email: " + username);
    }
}
