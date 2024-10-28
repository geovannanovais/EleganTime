package com.eleganTime.elegantime.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Salva um novo usuário no banco de dados após validar o CPF e encriptar a
     * senha.
     *
     * @param usuario O usuário a ser salvo.
     * @return O usuário salvo.
     * @throws IllegalArgumentException se o CPF for inválido.
     */
    public Usuario salvarUsuario(Usuario usuario) {
        // Valida o CPF antes de salvar o usuário
        if (!validarCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }

        // Encripta a senha antes de salvar
        String senhaEncriptada = senhaService.encriptarSenha(usuario.getSenha());
        usuario.setSenha(senhaEncriptada);

        // Salva o usuário no repositório
        return usuarioRepository.save(usuario);
    }

    /**
     * Lista todos os usuários.
     *
     * @return lista de todos os usuários.
     */
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Edita um usuário existente após validar o CPF e encriptar a senha se
     * alterada.
     *
     * @param usuario O usuário com as alterações.
     * @return O usuário atualizado.
     * @throws IllegalArgumentException se o CPF for inválido.
     */
    public Usuario editUsuario(Usuario usuario) {
        // Valida o CPF antes de editar o usuário
        if (!validarCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }

        // Encripta a nova senha antes de atualizar, se for diferente da atual
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
        if (usuarioExistente != null && !usuarioExistente.getSenha().equals(usuario.getSenha())) {
            String senhaEncriptada = senhaService.encriptarSenha(usuario.getSenha());
            usuario.setSenha(senhaEncriptada);
        }

        // Atualiza o usuário no repositório
        return usuarioRepository.save(usuario);
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @param id o ID do usuário.
     * @return o usuário encontrado ou null.
     */
    public Usuario buscarPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * Autentica um usuário pelo email e senha fornecidos.
     *
     * @param email          o email do usuário.
     * @param senhaInformada a senha informada para autenticação.
     * @return o usuário autenticado ou null se as credenciais forem inválidas.
     */
    public Usuario autenticarUsuario(String email, String senhaInformada) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null && senhaService.verificarSenha(senhaInformada, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }

    /**
     * Ativa o usuário com o ID fornecido.
     *
     * @param id o ID do usuário.
     */
    public void ativarUsuario(int id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setCondicaoDoUsuario(true);
            usuarioRepository.save(usuario);
        }
    }

    /**
     * Desativa o usuário com o ID fornecido.
     *
     * @param id o ID do usuário.
     */
    public void desativarUsuario(int id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setCondicaoDoUsuario(false);
            usuarioRepository.save(usuario);
        }
    }

    /**
     * Valida o CPF conforme regras específicas:
     * - Remove todos os caracteres não numéricos.
     * - Verifica se o CPF possui 11 dígitos e não é uma sequência repetida.
     * - Calcula e valida os dígitos verificadores.
     *
     * @param cpf O CPF a ser validado.
     * @return true se o CPF for válido, false caso contrário.
     */
    public static boolean validarCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigitoVerificador = 11 - (soma % 11);
        if (primeiroDigitoVerificador >= 10) {
            primeiroDigitoVerificador = 0;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigitoVerificador = 11 - (soma % 11);
        if (segundoDigitoVerificador >= 10) {
            segundoDigitoVerificador = 0;
        }

        return (Character.getNumericValue(cpf.charAt(9)) == primeiroDigitoVerificador) &&
                (Character.getNumericValue(cpf.charAt(10)) == segundoDigitoVerificador);
    }

    public Boolean validarSenha(Usuario usuario) {
        String senha = usuarioRepository.findByEmail(usuario.getEmail()).getSenha();
        Boolean valid = senhaService.verificarSenha(usuario.getSenha(), senha);
        return valid;
    }
}
