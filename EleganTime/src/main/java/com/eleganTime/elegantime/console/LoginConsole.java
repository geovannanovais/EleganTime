package com.eleganTime.elegantime.console;

import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class LoginConsole {

    private final UsuarioService usuarioService;
    private final Scanner sc = new Scanner(System.in);
    private Usuario usuarioAutenticado;

    @Autowired
    public LoginConsole(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void Login() {
        while (true) {
            System.out.println("===== LOGIN ======");
            System.out.print("Digite o email: ");
            String email = sc.nextLine();
            System.out.print("Digite a senha: ");
            String senha = sc.nextLine();

            usuarioAutenticado = usuarioService.autenticarUsuario(email, senha);

            if (usuarioAutenticado != null) {
                System.out.println("Login bem-sucedido! Bem-vindo(a) " + usuarioAutenticado.getNome());
                break;
            } else {
                System.out.println("Login falhou! Email ou senha inválidos.");
            }
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}