package controller;

import model.Usuario;
import service.UsuarioService;
import java.util.List;
import java.util.Scanner;

import static sqlDAO.UsuarioDAO.listar;

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
            //System.out.println("4. Deletar Usuário");
            System.out.println("5. Sair");

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
                //case 4:
                //deletarUsuario();
                //break;
                case 5:
                    continuar = false;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarUsuario() {
        System.out.println("Nome:");
        String nome = scanner.nextLine();
        System.out.println("CPF:");
        String cpf = scanner.nextLine();
        System.out.println("Email:");
        String email = scanner.nextLine();
        System.out.println("Grupo (Administrador ou Estoquista):");
        String grupo = scanner.nextLine();
        System.out.println("Senha:");
        String senha = scanner.nextLine();
        System.out.println("Condição do Usuário (true/false):");
        boolean condicaoDoUsuario = scanner.nextBoolean();

        Usuario novoUsuario = new Usuario(nome, cpf, email, grupo, senha, condicaoDoUsuario);
        boolean sucesso = usuarioService.cadastrarUsuario(novoUsuario);

        if (sucesso) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o usuário.");
        }
    }

    private void atualizarUsuario() {
        System.out.println("ID do Usuário a ser atualizado:");
        int idUsuario = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha após o número

        System.out.println("Nome:");
        String nome = scanner.nextLine();

        System.out.println("CPF:");
        String cpf = scanner.nextLine();

        System.out.println("Email:");
        String email = scanner.nextLine();

        System.out.println("Grupo (Administrador ou Estoquista):");
        String grupo = scanner.nextLine();

        System.out.println("Senha:");
        String senha = scanner.nextLine();


        System.out.println("Condição do Usuário (true/false):");
        boolean condicaoDoUsuario = scanner.nextBoolean();

        // Criando um objeto Usuario com as novas informações
        Usuario usuarioAtualizado = new Usuario(idUsuario, nome, cpf, email, grupo, senha, condicaoDoUsuario);

        // Passando o objeto Usuario para a camada de serviço para atualização
        boolean sucesso = usuarioService.atualizarUsuario(usuarioAtualizado);

        if (sucesso) {
            System.out.println("Usuário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o usuário.");
        }
    }

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
                    usuario.getCondicaoDoUsuario()? "Ativo" : "Inativo",
                    usuario.getSenha());
        }

    }

//    private void deletarUsuario() {
//        System.out.println("ID do Usuário a ser deletado:");
//        int idUsuario = scanner.nextInt();
//        boolean sucesso = usuarioService.deletarUsuario(idUsuario);
//        if (sucesso) {
//            System.out.println("Usuário deletado com sucesso!");
//        } else {
//            System.out.println("Erro ao deletar o usuário.");
//        }
//    }
}
