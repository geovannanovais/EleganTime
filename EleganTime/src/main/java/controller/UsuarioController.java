package controller;

import model.Produto;
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
        boolean loginBemSucedido = false;

        // Loop até que o login seja bem-sucedido ou o usuário decida sair
        while (!loginBemSucedido) {

            if (exibirTelaLogin()) {
                loginBemSucedido = true; // Login bem-sucedido
            } else {
                System.out.println("Falha no login. Deseja tentar novamente? (S/N)");
                String resposta = scanner.nextLine().trim().toLowerCase();
                if (resposta.equals("n")) {
                    System.out.println("Encerrando o sistema...");
                    return; // Encerra o sistema
                }
            }
        }

        // Se o login for bem-sucedido, entra no menu principal
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Gerenciar Usuários");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Sair");

            System.out.print("Escolha sua opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    menuUsuario();
                    break;
                case 2:
                    menuProduto();
                    break;
                case 3:
                    continuar = false;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }


    // Tela de login
    private boolean exibirTelaLogin() {
        System.out.println("------------ Tela de Login ------------");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Senha: ");
        String senha = scanner.nextLine().trim();

        // Chama o serviço para validar o login
        String resultadoLogin = usuarioService.validarLogin(email, senha);

        // Exibe o resultado da validação
        System.out.println(resultadoLogin);

        // Retorna true apenas se o login for bem-sucedido
        return resultadoLogin.equals("Login realizado com sucesso!");
    }


    private void menuUsuario() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nEscolha uma operação:");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Atualizar Usuário");
            System.out.println("3. Listar Usuários");
            System.out.println("4. Voltar");

            System.out.print("Escolha sua opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuario();
                    System.out.println();
                    atualizarUsuario();
                    break;
                case 3:
                    listarUsuario();
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void menuProduto() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nEscolha uma operação:");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Atualizar Produto");
            System.out.println("3. Listar Produtos");
            System.out.println("4. Voltar");

            System.out.print("Escolha sua opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    listarProduto();
                    System.out.println();
                    atualizarProduto();
                    break;
                case 3:
                    listarProduto();
                    break;
                case 4:
                    continuar = false;
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

        // Loop para forçar a preencher a senha
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

        // Loop para forçar a preencher a condicao do usuario
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
                condicaoDoUsuario = false;
            }
        } while (!condicaoInput.equals("true") && !condicaoInput.equals("false"));

        // Atualizando o usuario
        Usuario usuarioAtualizado = new Usuario(idUsuario, nome, cpf, email, grupo, senha, condicaoDoUsuario);
        boolean sucesso = usuarioService.atualizarUsuario(usuarioAtualizado);

        if (sucesso) {
            System.out.println("Usuário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o usuário.");
        }
    }

    public void listarUsuario() {
        List<Usuario> usuarios = usuarioService.listarUsuario();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }

        // Cabeçalho da tabela
        System.out.printf("%-10s %-20s %-30s %-15s %-15s%n",
                "ID", "Nome", "Email", "Status", "Grupo");

        // Linha de separação
        System.out.println("=".repeat(90));

        // Conteúdo da tabela
        for (Usuario usuario : usuarios) {
            System.out.printf("%-10d %-20s %-30s %-15s %-15s%n",
                    usuario.getIdUsuario(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getCondicaoDoUsuario() ? "Ativo" : "Inativo",
                    usuario.getGrupo());
        }
    }

    private void atualizarProduto() {
        System.out.println("ID do Produto a ser atualizado:");
        int idProduto = scanner.nextInt();
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

        // Loop para forçar a preencher a descrição
        String descricao;
        do {
            System.out.print("Descrição Detalhada: ");
            descricao = scanner.nextLine().trim();
            if (descricao.isEmpty()) {
                System.out.println("Erro: A descrição não pode estar vazia.");
            }
        } while (descricao.isEmpty());

        // Loop para forçar a preencher o preço
        double preco;
        do {
            System.out.print("Preço: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Erro: Preço deve ser um número decimal.");
                scanner.next(); // Consumir a entrada inválida
                System.out.print("Preço: ");
            }
            preco = scanner.nextDouble();
            scanner.nextLine(); // Consumir a quebra de linha após o número
            if (preco < 0) {
                System.out.println("Erro: O preço não pode ser negativo.");
            }
        } while (preco < 0);

        // Loop para forçar a preencher a quantidade em estoque
        int quantidadeEmEstoque;
        do {
            System.out.print("Quantidade em Estoque: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Erro: Quantidade em estoque deve ser um número inteiro.");
                scanner.next(); // Consumir a entrada inválida
                System.out.print("Quantidade em Estoque: ");
            }
            quantidadeEmEstoque = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o número
            if (quantidadeEmEstoque < 0) {
                System.out.println("Erro: A quantidade em estoque não pode ser negativa.");
            }
        } while (quantidadeEmEstoque < 0);

        // Loop para forçar a preencher a condição do produto
        boolean condicaoDoProduto;
        String condicaoInput;
        do {
            System.out.print("Condição do Produto (true/false): ");
            condicaoInput = scanner.nextLine().trim().toLowerCase();
            if (condicaoInput.equals("true")) {
                condicaoDoProduto = true;
            } else if (condicaoInput.equals("false")) {
                condicaoDoProduto = false;
            } else {
                System.out.println("Erro: A condição do produto deve ser 'true' ou 'false'.");
                condicaoDoProduto = false;
            }
        } while (!condicaoInput.equals("true") && !condicaoInput.equals("false"));

        // Atualizando o produto
        Produto produto = new Produto(idProduto, nome, descricao, preco, quantidadeEmEstoque, condicaoDoProduto);
        boolean sucesso = UsuarioService.atualizarProduto(produto);

        if (sucesso) {
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o produto.");
        }
    }


    private void cadastrarProduto() {

        // Loop para forçar a preencher o nome
        String nome;
        do {
            System.out.print("Nome: ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Erro: O nome não pode estar vazio.");
            }
        } while (nome.isEmpty());


        // Loop para forçar a preencher a descrição
        String descricao;
        do {
            System.out.print("Descrição Detalhada: ");
            descricao = scanner.nextLine().trim();
            if (descricao.isEmpty()) {
                System.out.println("Erro: A descrição não pode estar vazia.");
            }
        } while (descricao.isEmpty());

        // Loop para forçar a preencher o preço
        double preco;
        do {
            System.out.print("Preço: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Erro: Preço deve ser um número decimal.");
                scanner.next(); // Consumir a entrada inválida
                System.out.print("Preço: ");
            }
            preco = scanner.nextDouble();
            scanner.nextLine(); // Consumir a quebra de linha após o número
            if (preco < 0) {
                System.out.println("Erro: O preço não pode ser negativo.");
            }
        } while (preco < 0);

        // Loop para forçar a preencher a quantidade em estoque
        int quantidadeEmEstoque;
        do {
            System.out.print("Quantidade em Estoque: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Erro: Quantidade em estoque deve ser um número inteiro.");
                scanner.next(); // Consumir a entrada inválida
                System.out.print("Quantidade em Estoque: ");
            }
            quantidadeEmEstoque = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o número
            if (quantidadeEmEstoque < 0) {
                System.out.println("Erro: A quantidade em estoque não pode ser negativa.");
            }
        } while (quantidadeEmEstoque < 0);

        // Loop para forçar a preencher a condição do produto
        boolean condicaoDoProduto;
        String condicaoInput;
        do {
            System.out.print("Condição do Produto (true/false): ");
            condicaoInput = scanner.nextLine().trim().toLowerCase();
            if (condicaoInput.equals("true")) {
                condicaoDoProduto = true;
            } else if (condicaoInput.equals("false")) {
                condicaoDoProduto = false;
            } else {
                System.out.println("Erro: A condição do produto deve ser 'true' ou 'false'");
                condicaoDoProduto = false;
            }
        } while (!condicaoInput.equals("true") && !condicaoInput.equals("false"));

        // Cadastrando o produto
        Produto produto = new Produto(nome, descricao, preco, quantidadeEmEstoque, condicaoDoProduto);
        boolean sucesso = UsuarioService.cadastrarProduto(produto);

        if (sucesso) {
            System.out.println("Produto cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o produto.");
        }
    }


    public void listarProduto() {
        List<Produto> produtos = usuarioService.listarProduto();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }

        // Cabeçalho da tabela
        System.out.printf("%-10s %-30s %-15s %-20s %-20s%n",
                "ID", "Nome", "Valor", "Qtde Estoque", "Status");

        // Linha de separação
        System.out.println("=".repeat(90));

        // Conteúdo da tabela
        for (Produto produto : produtos) {
            System.out.printf("%-10s %-30s %-15s %-20s %-20s%n",
                    produto.getIdProduto(),
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getQuantidadeEmEstoque(),
                    produto.getCondicaoDoProduto() ? "Ativo" : "Inativo");
        }
    }

}

