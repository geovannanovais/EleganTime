package com.eleganTime.elegantime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    private SenhaService senhaService;

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizarUsuario(int id, Usuario usuario) {
        usuario.setIdUsuario(id);
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario autenticarUsuario(String email, String senhaInformada) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null && senhaService.verificarSenha(senhaInformada, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }

    public void ativarUsuario(int id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setCondicaoDoUsuario(true);
            usuarioRepository.save(usuario);
        }
    }

    public void desativarUsuario(int id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setCondicaoDoUsuario(false);
            usuarioRepository.save(usuario);
        }
    }
}