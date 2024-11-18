package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    // Método para salvar um novo usuário ou atualizar um existente
    public Usuario salvar(Usuario usuario) {
        if (usuario.getIdUsuario() != 0) {
            Usuario usuarioExistente = buscarPorId(usuario.getIdUsuario());
            if (usuarioExistente != null) {
                return atualizarUsuario(usuario.getIdUsuario(), usuario);
            } else {
                throw new RuntimeException("Usuário não encontrado para atualização");
            }
        } else {
            // Criação de um novo usuário sem encriptação de senha
            return usuarioRepository.save(usuario);
        }
    }

    // Método para atualizar as informações de um usuário existente
    public Usuario atualizarUsuario(int id, Usuario usuario) {
        Usuario usuarioExistente = buscarPorId(id);
        if (usuarioExistente != null) {
            usuarioExistente.setNome(usuario.getNome());
            usuarioExistente.setCpf(usuario.getCpf());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setGrupo(usuario.getGrupo());

            // Atualiza a senha somente se ela não for nula ou vazia
            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                usuarioExistente.setSenha(usuario.getSenha());  // Não criptografa, apenas atualiza
            }

            usuarioExistente.setCondicaoDoUsuario(usuario.isCondicaoDoUsuario());
            return usuarioRepository.save(usuarioExistente);
        } else {
            throw new RuntimeException("Usuário não encontrado para atualização");
        }
    }

    // Método para listar todos os usuários
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método para deletar um usuário pelo ID
    public void deletarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

    // Método para buscar um usuário pelo ID
    public Usuario buscarPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Método para autenticar um usuário comparando a senha sem criptografia
    public Usuario autenticarUsuario(String email, String password) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {  // Verifica se o usuário foi encontrado
            Usuario usuario = usuarioOptional.get();
            System.out.println("Senha armazenada: " + usuario.getSenha());
            System.out.println("Senha fornecida: " + password);

            // Compara a senha armazenada com a senha fornecida (sem criptografia)
            if (usuario.getSenha().equals(password)) {
                return usuario;  // Retorna o usuário se as senhas coincidirem
            }
        }

        return null;  // Retorna null se o usuário não for encontrado ou as senhas não coincidirem
    }

    // Método para verificar se o usuário tem o papel de "ADMIN"
    public boolean isAdmin(int usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario != null && "ADMIN".equals(usuario.getGrupo());
    }

    // Método para alterar o status de um usuário
    public void alterarStatusUsuario(int id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setCondicaoDoUsuario(!usuario.isCondicaoDoUsuario());  // Alterna o status
            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    // Método para buscar um usuário pelo email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
