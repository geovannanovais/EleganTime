package com.eleganTime.elegantime.console;

import com.eleganTime.elegantime.controller.UsuarioController;
import com.eleganTime.elegantime.model.Usuario;
import com.eleganTime.elegantime.service.SenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class UsuarioConsole {

    private final UsuarioController usuarioController;
    private final Scanner scanner = new Scanner(System.in);
    private final SenhaService senhaSevice = new SenhaService();
    private final LoginConsole loginConsole;

    @Autowired
    public UsuarioConsole(UsuarioController usuarioController, LoginConsole loginConsole) {
        this.usuarioController = usuarioController;
        this.loginConsole = loginConsole;
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("==== Menu de Usuários ====");
            System.out.println("1. Listar Usuários");
            if (isAdmin()) {
                System.out.println("2. Adicionar Usuário");
                System.out.println("3. Atualizar Usuário");
                System.out.println("4. Deletar Usuário");
                System.out.println("5. Ativar/Desativar usuário");
            }
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    if (isAdmin()) {
                        adicionarUsuario();
                    } else {
                        System.out.println("Você não tem permissão para adicionar usuários.");
                    }
                    break;
                case 3:
                    if (isAdmin()) {
                        atualizarUsuario();
                    } else {
                        System.out.println("Você não tem permissão para atualizar usuários.");
                    }
                    break;
                case 4:
                    if (isAdmin()) {
                        deletarUsuario();
                    } else {
                        System.out.println("Você não tem permissão para deletar usuários.");
                    }
                    break;
                case 5:
                    if (isAdmin()) {
                        ativarDesativar();
                    } else {
                        System.out.println("Você não tem permissão para ativar/desativar usuários.");
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");

                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private boolean isAdmin() {
        return "Administrador".equalsIgnoreCase(loginConsole.getUsuarioAutenticado().getGrupo());
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioController.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }
        System.out.printf("%-10s %-30s %-20s %-20s %-10s%n", "ID", "Nome", "Email", "Grupo", "Status");
        System.out.println("=".repeat(100));
        for (Usuario usuario : usuarios) {
            System.out.printf("%-10d %-30s %-20s %-20s %-10s%n",
                    usuario.getIdUsuario(), usuario.getNome(), usuario.getEmail(), usuario.getGrupo(), usuario.isCondicaoDoUsuario() ? "Ativo" : "Inativo");
        }
    }

    private void adicionarUsuario() {
        Usuario usuario = new Usuario();

        String nome;
        while (true) {
            System.out.print("Nome: ");
            nome = scanner.nextLine();
            if (!nome.isEmpty()) {
                usuario.setNome(nome);
                break;
            } else {
                System.out.println("O nome não pode ser vazio! Por favor, insira um nome válido.");
            }
        }


        String cpf;
        while (true) {
            System.out.print("CPF: ");
            cpf = scanner.nextLine().replaceAll("[^0-9]", "");

            if (!usuarioController.validarCpf(cpf)) {
                System.out.println("CPF inválido.");
            }else {
                usuario.setCpf(cpf);
                break;
            }
        }

        String grupo;
        while (true) {
            System.out.print("Grupo (Administrador / Estoquista): ");
            grupo = scanner.nextLine();

            if (grupo.equalsIgnoreCase("Administrador") || grupo.equalsIgnoreCase("Estoquista")) {
                usuario.setGrupo(grupo);
                break;
            } else {
                System.out.println("Grupo inválido! Por favor, escolha 'Administrador' ou 'Estoquista'.");
            }
        }

        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            if (!email.isEmpty()) {
                usuario.setEmail(email);
                break;
            } else {
                System.out.println("O email não pode ser vazio! Por favor, insira um email válido.");
            }
        }

        String senha;
        while (true) {
            System.out.print("Senha: ");
            senha = scanner.nextLine();
            if (senha.length() >= 8 && senha.matches(".*[A-Z].*") && senha.matches(".*[a-z].*") && senha.matches(".*\\d.*") && senha.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                usuario.setSenha(senhaSevice.encriptarSenha(senha));
                break;
            } else {
                System.out.println("A senha deve ter pelo menos 8 caracteres e incluir letras maiúsculas, minúsculas, números e caracteres especiais.");
            }
        }


        usuario.setCondicaoDoUsuario(true);

        usuarioController.criarUsuario(usuario);
        System.out.println("Usuário adicionado com sucesso!");

        System.out.println(usuario.toString());
    }

    private void atualizarUsuario() {
        System.out.print("ID do usuário a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Usuario usuario = usuarioController.buscarPorId(id);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.println("Atualizando usuário: " + usuario.getNome());

        String nome;
        while (true) {
            System.out.print("Novo Nome (atual: " + usuario.getNome() + "): ");
            nome = scanner.nextLine();
            if (!nome.isEmpty()) {
                usuario.setNome(nome);
                break;
            } else {
                System.out.println("O nome não pode ser vazio! Por favor, insira um nome válido.");
            }
        }


        String cpf;
        while (true) {
            System.out.print("Novo CPF (atual: " + usuario.getCpf() + "): ");
            cpf = scanner.nextLine();
            if (usuarioController.validarCpf(cpf)) {
                usuario.setCpf(cpf);
                break;
            } else {
                System.out.println("CPF inválido! Por favor, insira um CPF válido.");
            }
        }


        String email;
        while (true) {
            System.out.print("Novo Email (atual: " + usuario.getEmail() + "): ");
            email = scanner.nextLine();
            if (!email.isEmpty()) {
                usuario.setEmail(email);
                break;
            } else {
                System.out.println("O email não pode ser vazio! Por favor, insira um email válido.");
            }
        }

        String grupo;
        while (true) {
            System.out.print("Novo Grupo (atual: " + usuario.getGrupo() + "): ");
            grupo = scanner.nextLine();
            if (!grupo.isEmpty()) {
                usuario.setGrupo(grupo);
                break;
            } else {
                System.out.println("O grupo não pode ser vazio! Por favor, insira um grupo válido.");
            }
        }

        String senha;
        while (true) {
            System.out.print("Senha: ");
            senha = scanner.nextLine();
            if (senha.length() >= 8 && senha.matches(".*[A-Z].*") && senha.matches(".*[a-z].*") && senha.matches(".*\\d.*") && senha.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                usuario.setSenha(senhaSevice.encriptarSenha(senha));
                break;
            } else {
                System.out.println("A senha deve ter pelo menos 8 caracteres e incluir letras maiúsculas, minúsculas, números e caracteres especiais.");
            }
        }


        usuarioController.atualizarUsuario(id, usuario);
        System.out.println("Usuário atualizado com sucesso!");
    }

    private void deletarUsuario() {
        System.out.print("ID do usuário a ser deletado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        usuarioController.deletarUsuario(id);
        System.out.println("Usuário deletado com sucesso!");
    }


    public void ativarDesativar() {
        while (true) {
            System.out.println("===== gerenciar ativação/ desativação conta =====");
            System.out.print("Digite o ID do usuário: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Usuario usuario = usuarioController.buscarPorId(id);
            if (usuario == null) {
                System.out.println("Usuário não encontrado.");
                continue;
            }

            System.out.println("Usuário encontrado: " + usuario.getNome());
            System.out.println("Status atual: " + (usuario.isCondicaoDoUsuario() ? "Ativo" : "Inativo"));
            System.out.print("Deseja (1) Ativar ou (2) Desativar o usuário? ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                usuarioController.ativarUsuario(id);
                System.out.println("Usuário ativado com sucesso!");
                break;
            } else if (opcao == 2) {
                usuarioController.desativarUsuario(id);
                System.out.println("Usuário desativado com sucesso!");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }

}
