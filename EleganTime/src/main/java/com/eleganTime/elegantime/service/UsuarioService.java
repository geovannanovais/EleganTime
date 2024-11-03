package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Biblioteca de encriptação
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public Usuario salvar(Usuario usuario) {
        if (usuario.getIdUsuario() != 0) {
            Usuario usuarioExistente = buscarPorId(usuario.getIdUsuario());
            if (usuarioExistente != null) {
                return atualizarUsuario(usuario.getIdUsuario(), usuario);
            } else {
                throw new RuntimeException("Usuário não encontrado para atualização");
            }
        } else {
            // Criação de um novo usuário
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        }
    }

    public Usuario atualizarUsuario(int id, Usuario usuario) {
        Usuario usuarioExistente = buscarPorId(id);
        if (usuarioExistente != null) {
            usuarioExistente.setNome(usuario.getNome());
            usuarioExistente.setCpf(usuario.getCpf());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setGrupo(usuario.getGrupo());

            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
            }

            usuarioExistente.setCondicaoDoUsuario(usuario.isCondicaoDoUsuario());
            return usuarioRepository.save(usuarioExistente);
        } else {
            throw new RuntimeException("Usuário não encontrado para atualização");
        }
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }


    public void deletarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario autenticarUsuario(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {

            System.out.println("Senha armazenada: " + usuario.getSenha());
            System.out.println("Senha fornecida: " + password);
            if (usuario.getSenha().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean isAdmin(int usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario != null && "ADMIN".equals(usuario.getGrupo());
    }

    public void alterarStatusUsuario(int id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            // Alterna o status do usuário
            usuario.setCondicaoDoUsuario(!usuario.isCondicaoDoUsuario());
            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

}