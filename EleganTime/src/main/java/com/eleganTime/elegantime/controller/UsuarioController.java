package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    public Usuario buscarPorId(int id) {
        return usuarioService.buscarPorId(id);
    }

    public Usuario atualizarUsuario(int id, Usuario usuario) {
        return usuarioService.atualizarUsuario(id, usuario);
    }

    public void deletarUsuario(int id) {
        usuarioService.deletarUsuario(id);
    }

    public void ativarUsuario(int id) {
        usuarioService.ativarUsuario(id);
    }

    public void desativarUsuario(int id) {
        usuarioService.desativarUsuario(id);
    }

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
}