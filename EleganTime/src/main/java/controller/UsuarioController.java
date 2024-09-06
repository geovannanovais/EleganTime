package controller;

import model.Usuario;
import service.UsuarioService;

import java.util.List;
import java.util.Scanner;

public class UsuarioController {

    private UsuarioService usuarioService;
    private Scanner scanner;

    public UsuarioController() {
        usuarioService = new UsuarioService();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nEscolha uma operação:");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Atualizar Usuário");
            System.out.println("3. Listar Usuários");
            System.out.println("4. Sair");

            System.out.print("Escolha sua opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    atualizarUsuario();
                    break;
                case 3:
                    listar();
                    break;
                case 4:
                    continuar = false;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarUsuario() {

        // Loop para forçar a preencher o nome
        String nome;
        do {
            System.out.print("Nome: ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Erro: O nome não pode estar vazio.");
            }
        } while (nome.isEmpty());

        // Loop para forçar a preencher o cpf
        String cpf;
        do {
            System.out.print("CPF: ");
            cpf = scanner.nextLine().trim();
            if (cpf.contains(" ") || cpf.isEmpty()) {
                System.out.println("Erro: O CPF não pode conter espaços ou estar vazio.");
            }
        } while (cpf.contains(" ") || cpf.isEmpty());

        // // Loop para forçar a preencher o email
        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            if (email.contains(" ") || email.isEmpty()) {
                System.out.println("Erro: O Email não pode conter espaços ou estar vazio.");
            }
        } while (email.contains(" ") || email.isEmpty());

        // Loop para forçar a preencher o grupo
        String grupo;
        do {
            System.out.print("Grupo (Administrador ou Estoquista): ");
            grupo = scanner.nextLine().trim();
            if (grupo.contains(" ") || grupo.isEmpty() ||
                    (!grupo.equalsIgnoreCase("Administrador") && !grupo.equalsIgnoreCase("Estoquista"))) {
                System.out.println("Erro: Grupo inválido. Escolha 'Administrador' ou 'Estoquista'.");
            }
        } while (grupo.contains(" ") || grupo.isEmpty() ||
                (!grupo.equalsIgnoreCase("Administrador") && !grupo.equalsIgnoreCase("Estoquista")));

        // // Loop para forçar a preencher a senha
        String senha;
        do {
            System.out.print("Senha: ");
            senha = scanner.nextLine().trim();
            if (senha.length() < 5 || senha.contains(" ")) {
                System.out.println("Erro: A senha deve ter pelo menos 5 caracteres e não pode conter espaços.");
            }
        } while (senha.length() < 5 || senha.contains(" "));

        // Loop para forçar a preencher a condicao do usuario (false ou true)
        boolean condicaoDoUsuario;
        String condicaoInput;
        do {
            System.out.print("Condição do Usuário (true/false): ");
            condicaoInput = scanner.nextLine().trim().toLowerCase();
            if (condicaoInput.equals("true")) {
                condicaoDoUsuario = true;
            } else if (condicaoInput.equals("false")) {
                condicaoDoUsuario = false;
            } else {
                System.out.println("Erro: A condição do usuário deve ser 'true' ou 'false'.");
                condicaoDoUsuario = false; // Inicializar com um valor válido para continuar o loop
            }
        } while (!condicaoInput.equals("true") && !condicaoInput.equals("false"));

        // Salvando o novo usuario
        Usuario novoUsuario = new Usuario(nome, cpf, email, grupo, senha, condicaoDoUsuario);
        boolean sucesso = usuarioService.cadastrarUsuario(novoUsuario);

        if (sucesso) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o usuário.");
        }
    }

    // Atualizar o usuario
    private void atualizarUsuario() {
        System.out.println("ID do Usuário a ser atualizado:");
        int idUsuario = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha após o número

        // Loop para forçar a preencher o nome
        String nome;
        do {
            System.out.print("Nome: ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Erro: O nome não pode estar vazio.");
            }
        } while (nome.isEmpty());

        // Loop para forçar a preencher o cpf
        String cpf;
        do {
            System.out.print("CPF: ");
            cpf = scanner.nextLine().trim();
            if (cpf.contains(" ") || cpf.isEmpty()) {
                System.out.println("Erro: O CPF não pode conter espaços ou estar vazio.");
            }
        } while (cpf.contains(" ") || cpf.isEmpty());

        // Loop para forçar a preencher o email
        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            if (email.contains(" ") || email.isEmpty()) {
                System.out.println("Erro: O Email não pode conter espaços ou estar vazio.");
            }
        } while (email.contains(" ") || email.isEmpty());

        // Loop para forçar a preencher o grupo
        String grupo;
        do {
            System.out.print("Grupo (Administrador ou Estoquista): ");
            grupo = scanner.nextLine().trim();
            if (grupo.contains(" ") || grupo.isEmpty() ||
                    (!grupo.equalsIgnoreCase("Administrador") && !grupo.equalsIgnoreCase("Estoquista"))) {
                System.out.println("Erro: Grupo inválido. Escolha 'Administrador' ou 'Estoquista'.");
            }
        } while (grupo.contains(" ") || grupo.isEmpty() ||
                (!grupo.equalsIgnoreCase("Administrador") && !grupo.equalsIgnoreCase("Estoquista")));

        // Loop para forçar a preencher o senha
        String senha;
        do {
            System.out.print("Senha: ");
            senha = scanner.nextLine().trim();
            if (senha.length() < 5 || senha.contains(" ")) {
                System.out.println("Erro: A senha deve ter pelo menos 5 caracteres e não pode conter espaços.");
            }
        } while (senha.length() < 5 || senha.contains(" "));

        // Loop para forçar a preencher o condicaoDoUsuario, verificar o true e false
        boolean condicaoDoUsuario;
        String condicaoInput; // Para verificar se a entrada e false ou true
        do {
            System.out.print("Condição do Usuário (true/false): ");
            condicaoInput = scanner.nextLine().trim().toLowerCase();
            if (condicaoInput.equals("true")) {
                condicaoDoUsuario = true;
            } else if (condicaoInput.equals("false")) {
                condicaoDoUsuario = false;
            } else {
                System.out.println("Erro: A condição do usuário deve ser 'true' ou 'false'.");
                condicaoDoUsuario = false; // Inicializar com um valor válido para continuar o loop
            }
        } while (!condicaoInput.equals("true") && !condicaoInput.equals("false")); // continuara ate que a entrada do usuário seja "true" ou "false". Quando um desses valores for inserido, o loop termina.

        // Atualizando o usuario apos as validacoes
        Usuario usuarioAtualizado = new Usuario(idUsuario, nome, cpf, email, grupo, senha, condicaoDoUsuario);
        boolean sucesso = usuarioService.atualizarUsuario(usuarioAtualizado);

        if (sucesso) {
            System.out.println("Usuário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o usuário.");
        }
    }

    // Listando os usuarios
    public void listar() {
        List<Usuario> usuarios = usuarioService.listar();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }

        System.out.printf("%-10s %-20s %-15s %-30s %-10s %-10s %-15s%n",
                "ID", "Nome", "CPF", "Email", "Grupo", "Condicao", "Senha");

        System.out.println("-----------------------------------------------------------------------------------------------");

        for (Usuario usuario : usuarios) {
            System.out.printf("%-10d %-20s %-15s %-30s %-10s %-10s %-15s%n",
                    usuario.getIdUsuario(),
                    usuario.getNome(),
                    usuario.getCpf(),
                    usuario.getEmail(),
                    usuario.getGrupo(),
                    usuario.getCondicaoDoUsuario() ? "Ativo" : "Inativo",
                    usuario.getSenha());
        }
    }
}
